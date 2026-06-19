package io.github.alexistrejo11.architecture.college.grade.service;

import io.github.alexistrejo11.architecture.college.common.utils.response.Result;

public interface GradeValidationService {
    Result<Void> authorizeGradeById(Long gradeId);
    Result<Void> validateGrade(Long gradeId);
}
