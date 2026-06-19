package io.github.alexistrejo11.architecture.college.grade.service.implementation.Grade;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.grades.GradeStatus;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;
import io.github.alexistrejo11.architecture.college.grade.repository.GradeRepository;
import io.github.alexistrejo11.architecture.college.grade.service.AcademicHistoryService;
import io.github.alexistrejo11.architecture.college.grade.service.GradeValidationService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GradeValidationServiceImpl implements GradeValidationService {

    private final GradeRepository gradeRepository;
    private final AcademicHistoryService academicHistoryService;

    @Override
    public Result<Void> authorizeGradeById(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade with ID " + gradeId + " not found"));;

        if (grade.getGradeStatus() == GradeStatus.VALIDATED || grade.getGradeStatus() == GradeStatus.NOT_VALID) {
            return Result.error("Grade Already Authorized");
        }

        grade.setAsAuthorized();
        gradeRepository.save(grade);

        academicHistoryService.validateGrade(grade);
        return Result.success();
    }

    @Override
    public Result<Void> validateGrade(Long gradeId) {
        return null;
    }

}
