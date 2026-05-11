package io.github.alexistrejo11.architecture.college.enrollment.service.implementation;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.*;
import io.github.alexistrejo11.architecture.college.enrollment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.service.group.GroupFacadeService;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentRelationshipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentRelationshipServiceImpl implements EnrollmentRelationshipService {

    public final GradeRepository gradeRepository;
    public final StudentRepository studentRepository;
    public final ObligatorySubjectRepository obligatorySubjectRepository;
    public final ElectiveSubjectRepository electiveSubjectRepository;
    public final GroupRepository groupRepository;
    public final GroupFacadeService groupFacadeService;

    public Result<Group> validateExistingGroup(EnrollmentInsertDTO enrollmentInsertDTO) {
        Optional<Group> optionalGroup = groupRepository.findByGroupKeyAndSubjectKey(enrollmentInsertDTO.getGroupKey(), enrollmentInsertDTO.getSubjectKey());
        return optionalGroup.map(Result::success)
                .orElseGet(() -> Result.error("Invalid Keys Provided"));
    }

    @Override
    public EnrollmentRelationship getRelationshipData(Group group, String accountNumber) {
        EnrollmentRelationship enrollmentRelationship = new EnrollmentRelationship();
        Student student = studentRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        List<Grade> studentGrades = gradeRepository.findByStudentAccountNumber(accountNumber);

        if (group.getSubjectType() == SubjectType.OBLIGATORY) {
            Optional<ObligatorySubject> subjectOptional = obligatorySubjectRepository.findByKey(group.getSubjectKey());
            if (subjectOptional.isEmpty()) {
                throw new RuntimeException("Subject Not Found for key: " + group.getSubjectKey());
            }

            enrollmentRelationship.setObligatorySubject(subjectOptional.get());
        } else {
            Optional<ElectiveSubject> subjectOptional = electiveSubjectRepository.findByKey(group.getGroupKey());
            if (subjectOptional.isEmpty()) {
                throw new RuntimeException("Subject Not Found for key: " + group.getSubjectKey());
            }

            enrollmentRelationship.setElectiveSubject(subjectOptional.get());
        }

        enrollmentRelationship.setStudent(student);
        enrollmentRelationship.setGroup(group);
        enrollmentRelationship.setStudentGrades(studentGrades);

        return enrollmentRelationship;
    }

    @Override
    public Result<Void> takeSpot(Long groupId) {
       Result<Void> spotResult = groupFacadeService.takeSpot(groupId).join();
       if (!spotResult.isSuccess()) {
           return Result.error(spotResult.getErrorMessage());
       }

       return Result.success();
    }
}
