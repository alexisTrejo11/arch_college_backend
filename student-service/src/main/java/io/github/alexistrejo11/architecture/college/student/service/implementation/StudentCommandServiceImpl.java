package io.github.alexistrejo11.architecture.college.student.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.models.subject.ProfessionalLineModality;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.student.mappers.StudentMapper;
import io.github.alexistrejo11.architecture.college.student.model.Student;
import io.github.alexistrejo11.architecture.college.student.repository.StudentRepository;
import io.github.alexistrejo11.architecture.college.student.service.StudentCommandService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentCommandServiceImpl implements StudentCommandService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final AccountNumberGenerationService accountNumberGenerationService;
    private final String currentGenerationIncome =  AcademicData.getCurrentSchoolPeriod();

    @Override
    @Transactional
    public StudentDTO createStudent(StudentInsertDTO studentInsertDTO) {
        Student student = studentMapper.insertDtoToEntity(studentInsertDTO);
        String accountNumber = accountNumberGenerationService.generateStudentAccountNumber(student);

        student.initializeAcademicValues(accountNumber, currentGenerationIncome);
        studentRepository.saveAndFlush(student);

        return studentMapper.entityToDTO(student);
    }

    @Override
    @Transactional
    public void updateStudent(StudentInsertDTO studentInsertDTO, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + studentId + " not found"));

        studentMapper.updatePersonalData(student, studentInsertDTO);
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void setProfessionalLineData(String accountNumber, Long professionalLineId, ProfessionalLineModality professionalLineModality) {
        Student student = studentRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Entity with Account Number " + accountNumber + " not found"));

        if (student.getSemestersCompleted() < 6) {
            throw  new RuntimeException("Student not able to set professional line data");
        }

        student.setProfessionalLineId(professionalLineId);
        student.setProfessionalLineModality(professionalLineModality);

        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void increaseSemestersCursed(String accountNumber) {
        Student student = studentRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Entity with Account Number " + accountNumber + " not found"));

        student.increaseSemesterCompleted();

        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Entity with ID " + studentId + " not found");
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public boolean validateExistingStudent(String accountNumber) {
        return studentRepository.existsByAccountNumber(accountNumber);
    }
}