package io.github.alexistrejo11.architecture.college.student.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.common.utils.student.StudentFilter;
import io.github.alexistrejo11.architecture.college.student.mappers.StudentMapper;
import io.github.alexistrejo11.architecture.college.student.model.Student;
import io.github.alexistrejo11.architecture.college.student.repository.StudentRepository;
import io.github.alexistrejo11.architecture.college.student.service.StudentQueryService;
import io.github.alexistrejo11.architecture.college.student.repository.StudentSpecification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentQueryServiceImpl implements StudentQueryService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final String currentGenerationIncome =  AcademicData.getCurrentSchoolPeriod();

    @Override
    @Cacheable(value = "studentById", key = "#studentId")
    public Result<StudentDTO> getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent
                .map(student -> Result.success(studentMapper.entityToDTO(student)))
                .orElseGet(() -> Result.error("Student not found"));
    }

    @Override
    @Cacheable(value = "studentByAccountNumber", key = "#accountNumber")
    public Result<StudentDTO> getStudentByAccountNumber(String accountNumber) {
        Optional<Student> optionalStudent = studentRepository.findByAccountNumber(accountNumber);
        return optionalStudent
                .map(student -> Result.success(studentMapper.entityToDTO(student)))
                .orElseGet(() -> Result.error("Student not found"));
    }

    @Override
    public Page<StudentDTO> getStudentsSortedByFilterPageable(Pageable pageable, StudentFilter filter, String param) {
        Specification<Student> specification = StudentSpecification.withFilter(filter, param);

        Page<Student> students = studentRepository.findAll(specification, pageable);

        return students.map(studentMapper::entityToDTO);
    }

    @Override
    public Page<StudentDTO> getAllStudentsSortedByFilterPageable(Pageable pageable, StudentFilter filter) {
        Specification<Student> specification = StudentSpecification.withFilter(filter);
        Page<Student> students = studentRepository.findAll(specification, pageable);

        return students.map(studentMapper::entityToDTO);
    }
}
