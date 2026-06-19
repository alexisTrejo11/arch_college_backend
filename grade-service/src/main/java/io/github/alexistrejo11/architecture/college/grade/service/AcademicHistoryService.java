package io.github.alexistrejo11.architecture.college.grade.service;

import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.grade.model.AcademicHistory;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;

public interface AcademicHistoryService {
    void validateUniqueAcademicHistoryPerStudent(String accountNumber);
    void validateGrade(Grade grade);

    void initAcademicHistory(StudentDTO studentDTO, CareerDTO careerDTO);
    AcademicHistory getAcademicHistoryByAccountNumber(String accountNumber);
    void setGradeToAcademicHistory(Grade grade);
}
