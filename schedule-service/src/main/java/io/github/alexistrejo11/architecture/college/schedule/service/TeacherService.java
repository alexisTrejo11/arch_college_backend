package io.github.alexistrejo11.architecture.college.schedule.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.schedule.mappper.TeacherMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    public void createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.dtoToEntity(teacherDTO);
        teacherRepository.save(teacher);
    }


    public void deleteTeacher(String teacherAccountNumber) {
        Teacher teacher = teacherRepository.findByAccountNumber(teacherAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Can't Delete Teacher with Account Number " + teacherAccountNumber + " . Teacher not found"));

        teacherRepository.delete(teacher);
    }

}
