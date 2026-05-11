package io.github.alexistrejo11.architecture.college.grade.service.implementation.Grade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.models.grades.GradeStatus;
import io.github.alexistrejo11.architecture.college.common.models.grades.GradeType;
import io.github.alexistrejo11.architecture.college.grade.mapper.GradeMapper;
import io.github.alexistrejo11.architecture.college.grade.mapper.GroupMapper;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;
import io.github.alexistrejo11.architecture.college.grade.model.Group;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import io.github.alexistrejo11.architecture.college.grade.repository.GradeRepository;
import io.github.alexistrejo11.architecture.college.grade.repository.SubjectRepository;
import io.github.alexistrejo11.architecture.college.grade.service.GradeCommandService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeCommandServiceImpl implements GradeCommandService {

    private final GroupMapper groupMapper;
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final SubjectRepository subjectRepository;

    @Override
    public void initGradesFromEnrollments(Group group, List<EnrollmentDTO> enrollmentDTOS) {
        log.info("Initializing grades for group with ID {} and subject {}", group.getId(), group.getSubject().getSubjectName());

        List<Grade> grades = enrollmentDTOS.stream()
                .map(enrollmentDTO -> {
                    Subject subject = group.getSubject();

                    GradeType gradeType = subject.getSubjectCredits() == 0
                            ? GradeType.NO_CREDITS
                            : GradeType.CREDITABLE;

                    log.info("Creating grade for student with account number {} in group {} for school period {}",
                            enrollmentDTO.getStudentAccountNumber(), group.getId(), enrollmentDTO.getSchoolPeriod());

                    return Grade.builder()
                            .gradeStatus(GradeStatus.PENDING_RESULT)
                            .schoolPeriod(enrollmentDTO.getSchoolPeriod())
                            .studentAccountNumber(enrollmentDTO.getStudentAccountNumber())
                            .gradeType(gradeType)
                            .subject(subject)
                            .group(group)
                            .build();
                })
                .toList();

        gradeRepository.saveAll(grades);
        log.info("Successfully saved {} grades for group with ID {}", grades.size(), group.getId());
    }

    @Override
    public void deleteGradeById(Long gradeId) {
        log.info("Attempting to delete grade with ID {}", gradeId);

        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> {
                    log.warn("Grade with ID {} not found", gradeId);
                    return new NotFoundException("Grade with ID " + gradeId + " not found");
                });

        grade.setAsDeleted();
        gradeRepository.save(grade);
        log.info("Successfully marked grade with ID {} as deleted", gradeId);
    }
}
