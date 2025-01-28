package microservice.enrollment_service.Service;

import microservice.common_classes.DTOs.Enrollment.EnrollmentDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.SubjectType;
import microservice.enrollment_service.Mappers.EnrollmentMapper;
import microservice.enrollment_service.Model.Enrollment;
import microservice.enrollment_service.Model.Preload.Grade;
import microservice.enrollment_service.Model.Preload.ObligatorySubject;
import microservice.enrollment_service.Repository.EnrollmentRepository;
import microservice.enrollment_service.Service.Implementation.EnrollmentFinderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class EnrollmentFinderServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentFinderServiceImpl enrollmentFinderService;

    @Test
    void testGetByIdSuccess() {
        Long enrollmentId = 1L;
        Enrollment enrollment = new Enrollment();
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(enrollmentMapper.entityToDTO(enrollment)).thenReturn(enrollmentDTO);

        Result<EnrollmentDTO> result = enrollmentFinderService.getById(enrollmentId);

        assertTrue(result.isSuccess());
        assertEquals(enrollmentDTO, result.getData());

        verify(enrollmentRepository).findById(enrollmentId);
        verify(enrollmentMapper).entityToDTO(enrollment);
    }

    @Test
    void testGetByIdNotFound() {
        Long enrollmentId = 1L;

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        Result<EnrollmentDTO> result = enrollmentFinderService.getById(enrollmentId);

        assertFalse(result.isSuccess());
        assertEquals("Enrollment not found", result.getErrorMessage());

        verify(enrollmentRepository).findById(enrollmentId);
    }

    @Test
    void testGetByAccountNumberSuccess() {
        String studentAccountNumber = "12345";
        Enrollment enrollment = new Enrollment();
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();

        when(enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(studentAccountNumber, "currentPeriod"))
                .thenReturn(List.of(enrollment));
        when(enrollmentMapper.entityToDTO(enrollment)).thenReturn(enrollmentDTO);

        List<EnrollmentDTO> result = enrollmentFinderService.getByAccountNumber(studentAccountNumber);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(enrollmentDTO, result.get(0));

        verify(enrollmentRepository).findByStudentAccountNumberAndSchoolPeriod(studentAccountNumber, "currentPeriod");
        verify(enrollmentMapper).entityToDTO(enrollment);
    }
}
