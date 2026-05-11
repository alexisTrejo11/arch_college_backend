package io.github.alexistrejo11.architecture.college.grade.service.implementation.Grade;

import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectDTO;
import io.github.alexistrejo11.architecture.college.common.service.group.GroupFacadeService;
import io.github.alexistrejo11.architecture.college.common.service.student.StudentFacadeService;
import io.github.alexistrejo11.architecture.college.common.service.curriculum.AcademicCurriculumFacadeService;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GradeInsertDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GradeRelationshipsDTO;
import io.github.alexistrejo11.architecture.college.grade.service.GradeRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GradeRelationshipServiceImpl implements GradeRelationshipService {
    private final StudentFacadeService studentFacadeService;
    private final AcademicCurriculumFacadeService academicCurriculumFacadeService;
    private final GroupFacadeService groupFacadeService;

    @Autowired
    public GradeRelationshipServiceImpl(@Qualifier("StudentFacadeServiceImpl") StudentFacadeService studentFacadeService,
                                        @Qualifier("AcademicCurriculumFacadeServiceImpl") AcademicCurriculumFacadeService academicCurriculumFacadeService,
                                        @Qualifier("GroupFacadeServiceImpl") GroupFacadeService groupFacadeService) {
        this.studentFacadeService = studentFacadeService;
        this.academicCurriculumFacadeService = academicCurriculumFacadeService;
        this.groupFacadeService = groupFacadeService;
    }

    @Override
    public Result<GradeRelationshipsDTO> validateGradeRelationship(GradeInsertDTO gradeInsertDTO) {
        Long groupId = gradeInsertDTO.getGroupId();
        String studentAccountNumber = gradeInsertDTO.getStudentAccountNumber();

        CompletableFuture<? extends SubjectDTO> subjectFuture = resolveSubjectFuture(
                gradeInsertDTO.getSubjectId(),
                gradeInsertDTO.getSubjectType()
        );

        CompletableFuture<GroupDTO> groupFuture = groupFacadeService.getGroupById(groupId);
        CompletableFuture<StudentDTO> studentFuture = studentFacadeService.getStudentByAccountNumberAsync(studentAccountNumber);

        return CompletableFuture.allOf(subjectFuture, groupFuture, studentFuture).thenApply(v -> {
            SubjectDTO subjectDTO = subjectFuture.join();
            GroupDTO groupDTO = groupFuture.join();
            StudentDTO studentDTO = studentFuture.join();

            if (subjectDTO == null) return Result.<GradeRelationshipsDTO>error("Invalid AcademicCurriculumService");
            if (groupDTO == null) return Result.<GradeRelationshipsDTO>error("Invalid Group");
            if (studentDTO == null) return Result.<GradeRelationshipsDTO>error("Invalid Student");

            GradeRelationshipsDTO gradeRelationshipsDTO = GradeRelationshipsDTO.builder()
                    .groupDTO(groupDTO)
                    .studentDTO(studentDTO)
                    .subjectDTO(subjectDTO)
                    .build();

            return Result.success(gradeRelationshipsDTO);
        }).join();
    }

    private CompletableFuture<? extends SubjectDTO> resolveSubjectFuture(Long subjectId, SubjectType subjectType) {
        if (subjectType == SubjectType.OBLIGATORY) {
            return academicCurriculumFacadeService.getOrdinarySubjectById(subjectId);
        } else if (subjectType == SubjectType.ELECTIVE) {
            return academicCurriculumFacadeService.getElectiveSubjectById(subjectId);
        }
        return CompletableFuture.completedFuture(null);
    }

}
