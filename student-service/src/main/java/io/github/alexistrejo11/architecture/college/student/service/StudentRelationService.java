package io.github.alexistrejo11.architecture.college.student.service;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;

public interface StudentRelationService {
    Result<Void> validateExistingCareerId(Long careerId);
    Result<Void> validateProfessionalLineId(Long professionalLine);

    void initAcademicHistoryAsync(StudentDTO studentDTO);
}
