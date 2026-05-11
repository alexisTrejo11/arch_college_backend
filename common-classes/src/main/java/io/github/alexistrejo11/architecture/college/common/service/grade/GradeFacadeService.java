package io.github.alexistrejo11.architecture.college.common.service.grade;

import io.github.alexistrejo11.architecture.college.common.dto.Grade.GradeDTO;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GradeFacadeService {
    CompletableFuture<GradeDTO> getGradeById(Long gradeId);
    CompletableFuture<List<GradeDTO>> getGradesByStudentAccountNumber(String accountNumber);
    CustomPage<GradeDTO> getGradesByCareerPageable(int page, int pageSize);

}