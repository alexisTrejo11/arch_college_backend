package io.github.alexistrejo11.architecture.college.account.service.implemenation;

import io.github.alexistrejo11.architecture.college.account.service.AccountNumberValidator;
import io.github.alexistrejo11.architecture.college.account.service.PasswordService;
import io.github.alexistrejo11.architecture.college.account.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.ProfileDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.SignupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.UserDTO;
import io.github.alexistrejo11.architecture.college.common.service.student.StudentFacadeService;
import io.github.alexistrejo11.architecture.college.common.service.teacher.TeacherFacadeService;
import io.github.alexistrejo11.architecture.college.account.mapper.UserMapper;
import io.github.alexistrejo11.architecture.college.account.model.Role;
import io.github.alexistrejo11.architecture.college.account.model.User;
import io.github.alexistrejo11.architecture.college.account.repository.RoleRepository;
import io.github.alexistrejo11.architecture.college.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentFacadeService studentFacadeService;
    private final TeacherFacadeService teacherFacadeService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           @Qualifier("StudentFacadeServiceImpl") StudentFacadeService studentFacadeService,
                           @Qualifier("TeacherFacadeServiceImpl") TeacherFacadeService teacherFacadeService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.studentFacadeService = studentFacadeService;
        this.teacherFacadeService = teacherFacadeService;
    }

    @Override
    @Transactional
    public UserDTO createUser(SignupDTO signupDTO, String roleName) {
        String hashedPassword = PasswordService.hashPassword(signupDTO.getPassword());
        User user = User.builder()
                .email(signupDTO.getEmail())
                .phoneNumber(signupDTO.getPhoneNumber())
                .username(signupDTO.getAccountNumber())
                .password(hashedPassword)
                .joinedAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.saveAndFlush(user);

        addRole(user, roleName);

        log.info("User successfully created for user with account number [{}]", user.getUsername());
        return userMapper.entityToDTO(user);
    }

    @Override
    @Cacheable(value = "userById", key = "#userId")
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return userMapper.entityToDTO(user);
    }

    private void addRole(User user, String roleName) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        if (optionalRole.isEmpty()) {
            Role newRole = new Role(roleName);
            roleRepository.saveAndFlush(newRole);

            user.getRoles().add(newRole);
            userRepository.save(user);
        } else {
            user.getRoles().add(optionalRole.get());
            userRepository.save(user);
        }
    }

    @Async("taskExecutor")
    public void addMemberRelationAsync(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
         User user = optionalUser.get();

       boolean isStudentFormat = AccountNumberValidator.isStudentAccountNumber(username);
       if (isStudentFormat) {
            CompletableFuture<StudentDTO> studentFuture = studentFacadeService.getStudentByAccountNumberAsync(username);
            Long studentId = studentFuture.join().getId();

            user.setStudentId(studentId);
            userRepository.save(user);

            log.info("Student with ID({}) assigned to user ID({})", studentId, user.getId());
        } else {
           CompletableFuture<TeacherDTO> teacherFuture = teacherFacadeService.getTeacherByAccountNumber(username);
           Long teacherId = teacherFuture.join().getId();

           user.setTeacherId(teacherId);
           userRepository.save(user);

           log.info("Teacher with ID({}) assigned to user ID({})", teacherId, user.getId());
        }
    }

    @Override
    @Cacheable(value = "profileDataUsername", key = "#username")
    public ProfileDTO getProfileDataByUsername(String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("Invalid User"));

    StudentDTO studentDTO = null;
    if (user.getStudentId() != null) {
       CompletableFuture<StudentDTO> studentFuture = studentFacadeService.getStudentByAccountNumberAsync(user.getUsername());
        studentDTO = studentFuture.join();
    }

    ProfileDTO profileDTO = userMapper.entityToProfileDTO(user);
    userMapper.studentDTOToProfileDTO(profileDTO, studentDTO);
    profileDTO.setMainRole(getMainRole(user));

    return profileDTO;
    }

    private String getMainRole(User user) {
        if (user.getStudentId() != null) {
            return "Student";
        }

        if (user.getTeacherId() != null) {
            return "Teacher";
        }

        return "";
    }
}
