package io.github.alexistrejo11.architecture.college.student.service;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.models.subject.ProfessionalLineModality;

public interface StudentCommandService {
    void increaseSemestersCursed(String accountNumber);
    void setProfessionalLineData(String accountNumber, Long professionalLineId, ProfessionalLineModality professionalLineModality);

    StudentDTO createStudent(StudentInsertDTO studentInsertDTO);
    void updateStudent(StudentInsertDTO studentInsertDTO,  Long studentId);
    void deleteStudent(Long studentId);

    boolean validateExistingStudent(String accountNumber);
}
