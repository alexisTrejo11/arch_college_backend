package io.github.alexistrejo11.architecture.college.grade.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.grade.model.Group;

import java.util.List;

public interface GradeCommandService {
    void initGradesFromEnrollments(Group group, List<EnrollmentDTO> enrollmentDTOS);
    void deleteGradeById(Long enrollmentId);
}
