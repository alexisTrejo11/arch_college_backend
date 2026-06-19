package io.github.alexistrejo11.architecture.college.enrollment.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.EnrollmentMapper;
import io.github.alexistrejo11.architecture.college.enrollment.model.Enrollment;
import io.github.alexistrejo11.architecture.college.enrollment.repository.EnrollmentRepository;
import io.github.alexistrejo11.architecture.college.enrollment.service.implementation.EnrollmentFinderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

        Optional<EnrollmentDTO> result = enrollmentFinderService.getById(enrollmentId);

        assertTrue(result.isPresent());
        assertEquals(enrollmentDTO, result.get());

        verify(enrollmentRepository).findById(enrollmentId);
        verify(enrollmentMapper).entityToDTO(enrollment);
    }

    @Test
    void testGetByIdNotFound() {
        Long enrollmentId = 1L;

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        Optional<EnrollmentDTO> result = enrollmentFinderService.getById(enrollmentId);

        assertTrue(result.isEmpty());

        verify(enrollmentRepository).findById(enrollmentId);
    }

    @Test
    void testGetByAccountNumberSuccess() {
        String studentAccountNumber = "12345";
        Enrollment enrollment = new Enrollment();
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();

        when(enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(eq(studentAccountNumber), anyString()))
                .thenReturn(List.of(enrollment));
        when(enrollmentMapper.entityToDTO(enrollment)).thenReturn(enrollmentDTO);

        List<EnrollmentDTO> result = enrollmentFinderService.getByAccountNumber(studentAccountNumber);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(enrollmentDTO, result.get(0));

        verify(enrollmentRepository).findByStudentAccountNumberAndSchoolPeriod(eq(studentAccountNumber), anyString());
        verify(enrollmentMapper).entityToDTO(enrollment);
    }
}
