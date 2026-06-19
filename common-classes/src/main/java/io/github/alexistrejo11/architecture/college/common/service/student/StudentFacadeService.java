package io.github.alexistrejo11.architecture.college.common.service.student;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;
import io.github.alexistrejo11.architecture.college.common.models.subject.ProfessionalLineModality;

import java.util.concurrent.CompletableFuture;

public interface StudentFacadeService {
    CompletableFuture<Boolean> validateExisitingStudentAsync(String accountNumber);
    CompletableFuture<StudentDTO> getStudentByAccountNumberAsync(String accountNumber);
    CompletableFuture<Void> increaseSemesterCompletedAsync(String studentAccount);
    CompletableFuture<Void> setProfessionalLineDataAsync(String studentAccount, Long professionalLineId, ProfessionalLineModality professionalLineModality);

    CustomPage<StudentDTO> getStudentsPageable(int page, int pageSize);
}
