package io.github.alexistrejo11.architecture.college.teacher.service.Implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.teacher.service.dto.TeacherInsertDTO;
import io.github.alexistrejo11.architecture.college.teacher.mappers.TeacherMapper;
import io.github.alexistrejo11.architecture.college.teacher.queue.rabbitmq.TeacherMessageSender;
import io.github.alexistrejo11.architecture.college.teacher.model.Teacher;
import io.github.alexistrejo11.architecture.college.teacher.repository.TeacherRepository;
import io.github.alexistrejo11.architecture.college.teacher.service.TeacherCommandService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherCommandServiceImpl implements TeacherCommandService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final AccountNumberService accountNumberService;
    private final TeacherMessageSender teacherMessageSender;

    @Override
    public void createTeacher(TeacherInsertDTO teacherInsertDTO) {
        Teacher teacher = teacherMapper.insertDtoToEntity(teacherInsertDTO);
        String accountNumber = accountNumberService.generateTeacherAccountNumber(teacher);
        teacher.setAccountNumber(accountNumber);

        teacherRepository.save(teacher);
        log.info("Teacher created with Account Number: {}", teacher.getAccountNumber());

        teacherMessageSender.sendTeacherCreation(teacher);
        log.info("Teacher creation message sent for account number: {}", teacher.getAccountNumber());
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + teacherId + " not found"));

        teacherRepository.delete(teacher);
        log.info("Teacher with ID {} deleted successfully: {}", teacherId, teacher);

        teacherMessageSender.sendTeacherDeletion(teacher.getAccountNumber());
        log.info("Teacher deletion message sent for account number: {}", teacher.getAccountNumber());
    }

    @Override
    public boolean validateExistingTeacher(String teacherAccountNumber) {
        return teacherRepository.existsByAccountNumber(teacherAccountNumber);
    }
}
