package io.github.alexistrejo11.architecture.college.grade.service;

import io.github.alexistrejo11.architecture.college.common.dto.Grade.GradeDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.model.credits.GradeFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GradeQueryService {
    Result<GradeDTO> getGradeById(Long enrollmentId);
    Page<GradeDTO> getPendingValidationGrades(Pageable pageable);
    Page<GradeDTO> getGradesByFilters(GradeFinderFilter gradeFilter, Pageable pageable);
    List<GradeDTO> getAnnuallyGradesByStudentAccountNumber(String studentAccountNumber);
    List<GradeDTO> getCurrentGradesByStudentAccountNumber(String studentAccountNumber);
}
