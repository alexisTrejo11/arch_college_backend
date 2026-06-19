package io.github.alexistrejo11.architecture.college.enrollment.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Group;

public interface EnrollmentRelationshipService {
    Result<Group> validateExistingGroup(EnrollmentInsertDTO enrollmentInsertDTO);
    EnrollmentRelationship getRelationshipData(Group group, String accountNumber);
    Result<Void> takeSpot(Long groupId);
}
