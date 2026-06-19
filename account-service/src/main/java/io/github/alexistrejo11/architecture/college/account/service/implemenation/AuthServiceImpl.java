package io.github.alexistrejo11.architecture.college.account.service.implemenation;

import io.github.alexistrejo11.architecture.college.account.service.AccountNumberValidator;
import io.github.alexistrejo11.architecture.college.account.service.AuthService;
import io.github.alexistrejo11.architecture.college.account.service.PasswordService;
import io.github.alexistrejo11.architecture.college.common.dto.User.LoginDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.RoleDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.SignupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.UserDTO;
import io.github.alexistrejo11.architecture.college.common.service.student.StudentFacadeService;
import io.github.alexistrejo11.architecture.college.common.service.teacher.TeacherFacadeService;
import io.github.alexistrejo11.architecture.college.common.config.jwt.JWTSecurity;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.account.mapper.UserMapper;
import io.github.alexistrejo11.architecture.college.account.model.User;
import io.github.alexistrejo11.architecture.college.account.repository.UserRepository;
import io.github.alexistrejo11.architecture.college.account.controller.dto.JWTResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTSecurity jwtSecurity;
    private final StudentFacadeService studentFacadeService;
    private final TeacherFacadeService teacherFacadeService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           JWTSecurity jwtSecurity,
                           @Qualifier("StudentFacadeServiceImpl") StudentFacadeService studentFacadeService,
                           @Qualifier("TeacherFacadeServiceImpl") TeacherFacadeService teacherFacadeService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtSecurity = jwtSecurity;
        this.studentFacadeService = studentFacadeService;
        this.teacherFacadeService = teacherFacadeService;
    }

    @Override
    public Result<Void> validateSignupCredentials(SignupDTO signupDTO) {
        boolean isEmailAvailable = validateUniqueEmail(signupDTO.getEmail());
        if (!isEmailAvailable) {
            return Result.error("email already taken");
        }

        if (signupDTO.getPhoneNumber() != null) {
            boolean isPhoneAvailable = validateUniquePhoneNumber(signupDTO.getPhoneNumber());
            if (!isPhoneAvailable) {
                return Result.error("phone number already taken");
            }
        }

        return Result.success();
    }

    @Override
    public Result<UserDTO> validateLoginCredentials(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getAccountNumber());
        if (optionalUser.isEmpty()) {
            return Result.error("Invalid account number");
        }

        User user = optionalUser.get();

        boolean isPasswordCorrect = PasswordService.validatePassword(loginDTO.getPassword(), user.getPassword());
        if (!isPasswordCorrect) {
            return Result.error("Wrong Password");
        }

        return Result.success(getCachedUserDTO(user));
    }

    @Cacheable(value = "loginCache", key = "#user.username")
    public UserDTO getCachedUserDTO(User user) {
        return userMapper.entityToDTO(user);
    }


    @Override
    public Result<Void> validateExistingMember(String accountNumber) {
        boolean isStudentFormat = AccountNumberValidator.isStudentAccountNumber(accountNumber);
        if (!isStudentFormat) {
            return validateStudent(accountNumber);
        }
        else {
            return validateTeacher(accountNumber);
        }
    }

    @Override
    public JWTResponseDTO processLogin(UserDTO userDTO) {
        List<String> roleName = userDTO.getRoles().stream().map(RoleDTO::getName).toList();
        String accessToken = jwtSecurity.generateAccessToken(userDTO.getId(), userDTO.getUsername(), roleName);
        String refreshToken = jwtSecurity.generateRefreshToken(userDTO.getId());

        updateLastLoginAsync(userDTO.getEmail());

        return new JWTResponseDTO(refreshToken, accessToken);
    }

    @Override
    public JWTResponseDTO proccesSingup(UserDTO userDTO) {
        List<String> roleName = userDTO.getRoles().stream().map(RoleDTO::getName).toList();
        String accessToken = jwtSecurity.generateAccessToken(userDTO.getId(), userDTO.getUsername(), roleName);
        String refreshToken = jwtSecurity.generateRefreshToken(userDTO.getId());

        return new JWTResponseDTO(refreshToken, accessToken);
    }

    @Async("taskExecutor")
    public void updateLastLoginAsync(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }


    @Override
    public Result<Void> validatePasswordFormat(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (password == null || !password.matches(passwordPattern)) {
            return Result.error("Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one digit, and one special character(@,$,!,%,*,?,&).");
        }

        return Result.success();
    }



    public Result<Void> validateTeacher(String accountNumber) {
        CompletableFuture<Boolean> exisitingTeacherFuture = teacherFacadeService.validateExisitingTeacher(accountNumber);
        CompletableFuture<Optional<User>> optionalUserFuture = CompletableFuture.supplyAsync(() -> userRepository.findByUsername(accountNumber)
        );

        return CompletableFuture.allOf(exisitingTeacherFuture, optionalUserFuture)
                .thenApply(v -> {
                    Boolean isTeacherExisting = exisitingTeacherFuture.join();
                    if (!isTeacherExisting) {
                        return Result.<Void>error("Invalid account number");
                    }

                    Optional<User> optionalUser = optionalUserFuture.join();
                    if (optionalUser.isPresent()) {
                        return Result.<Void>error("Teacher already has an account");
                    }

                    return Result.success();
                }).join();
    }

    @Override
    public Result<Void> validateStudent(String accountNumber) {
        CompletableFuture<Boolean> existingStudentFuture = studentFacadeService.validateExisitingStudentAsync(accountNumber);
        CompletableFuture<Optional<User>> optionalUserFuture = CompletableFuture.supplyAsync(() -> userRepository.findByUsername(accountNumber)
        );

        return CompletableFuture.allOf(existingStudentFuture, optionalUserFuture)
                .thenApply(v -> {
                    Boolean isStudentExisting = existingStudentFuture.join();
                    if (!isStudentExisting) {
                        return Result.<Void>error("Invalid account number");
                    }

                    Optional<User> optionalUser = optionalUserFuture.join();
                    if (optionalUser.isPresent()) {
                        return Result.<Void>error("Student already has an account");
                    }

                    return Result.success();
                }).join();
    }

    private boolean validateUniquePhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isEmpty();

    }

    private boolean validateUniqueEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
