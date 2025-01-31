package microservice.enrollment_service.Service.Implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.common_classes.DTOs.Enrollment.EnrollmentInsertDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.Schedule.AcademicData;
import microservice.common_classes.Utils.SubjectType;
import microservice.enrollment_service.DTOs.EnrollmentRelationship;
import microservice.enrollment_service.Model.Enrollment;
import microservice.enrollment_service.Model.Preload.ObligatorySubject;
import microservice.enrollment_service.Model.Preload.Student;
import microservice.enrollment_service.Repository.EnrollmentRepository;
import microservice.enrollment_service.Service.EnrollmentCommandService;
import org.springframework.beans.factory.annotation.Autowired;
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
