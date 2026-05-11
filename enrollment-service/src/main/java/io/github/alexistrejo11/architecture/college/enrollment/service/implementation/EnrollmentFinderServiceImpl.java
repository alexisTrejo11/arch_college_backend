package io.github.alexistrejo11.architecture.college.enrollment.service.implementation;

import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.EnrollmentMapper;
import io.github.alexistrejo11.architecture.college.enrollment.model.Enrollment;
import io.github.alexistrejo11.architecture.college.enrollment.repository.EnrollmentRepository;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnrollmentFinderServiceImpl implements EnrollmentFinderService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final String CURRENT_SCHOOL_PERIOD = AcademicData.getCurrentSchoolPeriod();

    @Autowired
    public EnrollmentFinderServiceImpl(EnrollmentRepository enrollmentRepository,
                                                 EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    @Cacheable(value = "enrollmentById", key = "#enrollmentId")
    public Optional<EnrollmentDTO> getById(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        return optionalEnrollment.map(enrollmentMapper::entityToDTO);
    }


    @Override
    @Cacheable(value = "enrollmentByAccountNumber", key = "#studentAccountNumber")
    public List<EnrollmentDTO> getByAccountNumber(String studentAccountNumber) {
        List<Enrollment> currentEnrollments = enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(studentAccountNumber, CURRENT_SCHOOL_PERIOD);
        return currentEnrollments.stream()
                .map(enrollmentMapper::entityToDTO)
                .toList();
    }
}
