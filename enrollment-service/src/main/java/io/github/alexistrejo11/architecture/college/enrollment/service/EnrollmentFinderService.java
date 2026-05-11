package io.github.alexistrejo11.architecture.college.enrollment.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;

import java.util.List;
import java.util.Optional;

public interface EnrollmentFinderService {
    Optional<EnrollmentDTO> getById(Long id);
    List<EnrollmentDTO> getByAccountNumber(String studentAccountNumber);
}
