package microservice.enrollment_service.Service;

import microservice.common_classes.DTOs.Enrollment.EnrollmentDTO;
import microservice.common_classes.Utils.Response.Result;

import java.util.List;
import java.util.Optional;

public interface EnrollmentFinderService {
    Optional<EnrollmentDTO> getById(Long id);
    List<EnrollmentDTO> getByAccountNumber(String studentAccountNumber);
}
