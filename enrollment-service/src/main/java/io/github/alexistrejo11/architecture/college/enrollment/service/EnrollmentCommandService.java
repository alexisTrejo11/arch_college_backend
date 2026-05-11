package io.github.alexistrejo11.architecture.college.enrollment.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;

public interface EnrollmentCommandService {
    void createEnrollment(EnrollmentRelationship enrollmentRelationship, EnrollmentInsertDTO enrollmentInsertDTO);
    Result<Void> deleteEnrollment(String groupKey, String subjectKey, String studentAccountNumber);
    void deleteEnrollment(Long enrollmentId);
}
