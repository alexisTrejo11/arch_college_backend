package io.github.alexistrejo11.architecture.college.enrollment.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;

public interface EnrollmentValidationService {
    Result<Void> validateEnrollment(EnrollmentInsertDTO enrollmentInsertDTO, EnrollmentRelationship enrollmentRelationship, String accountNumber);
}
