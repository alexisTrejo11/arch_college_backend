package io.github.alexistrejo11.architecture.college.enrollment.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;
import io.github.alexistrejo11.architecture.college.enrollment.model.Enrollment;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ObligatorySubject;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Student;
import io.github.alexistrejo11.architecture.college.enrollment.repository.EnrollmentRepository;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentCommandService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentEnrollmentCommandServiceImpl implements EnrollmentCommandService {

    private final EnrollmentRepository enrollmentRepository;
    private final String CURRENT_SCHOOL_PERIOD = AcademicData.getCurrentSchoolPeriod();


    // TODO: IMPLEMENT ELECTIVE CREATION
    @Override
    @Transactional
    public void createEnrollment(EnrollmentRelationship enrollmentRelationship, EnrollmentInsertDTO enrollmentInsertDTO) {
        Student student = enrollmentRelationship.getStudent();
        ObligatorySubject subject = enrollmentRelationship.getObligatorySubject();

        Enrollment groupEnrollment = buildEnrollment(enrollmentRelationship, enrollmentInsertDTO);

        enrollmentRepository.save(groupEnrollment);

        log.info("Enrollment created: groupKey={}, subjectKey={}, studentAccountNumber={}",
                enrollmentInsertDTO.getGroupKey(), subject.getKey(), student.getAccountNumber());
    }

    @Override
    public Result<Void> deleteEnrollment(String groupKey, String subjectKey, String studentAccountNumber) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByGroupKeyAndSubjectKeyAndStudentAccountNumber(
                groupKey, subjectKey, studentAccountNumber);

        if (optionalEnrollment.isEmpty()) {
            return Result.error("Enrollment not found");
        }

        enrollmentRepository.delete(optionalEnrollment.get());

        log.info("Enrollment deleted: groupKey={}, subjectKey={}, studentAccountNumber={}",
                groupKey, subjectKey, studentAccountNumber);

        return Result.success();
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new EntityNotFoundException("Enrollment with ID " + enrollmentId + " not found");
        }

        enrollmentRepository.deleteById(enrollmentId);

        log.info("Enrollment deleted: ID={}", enrollmentId);
    }

    private Enrollment buildEnrollment(EnrollmentRelationship enrollmentRelationship,
                                      EnrollmentInsertDTO enrollmentInsertDTO) {
        Student student = enrollmentRelationship.getStudent();
        ObligatorySubject subject = enrollmentRelationship.getObligatorySubject();

        return Enrollment.builder()
                .enrollmentDate(LocalDateTime.now())
                .groupId(enrollmentRelationship.getGroup().getId())
                .groupKey(enrollmentInsertDTO.getGroupKey())
                .studentAccountNumber(student.getAccountNumber())
                .subjectId(subject.getId())
                .subjectCredits(subject.getCredits())
                .subjectName(subject.getName())
                .subjectType(SubjectType.OBLIGATORY)
                .subjectKey(enrollmentInsertDTO.getSubjectKey())
                .schoolPeriod(CURRENT_SCHOOL_PERIOD)
                .build();
    }
}
