package io.github.alexistrejo11.architecture.college.common.dto.Grade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitAcademicHistory {
    @NotNull
    @Valid
    private StudentDTO student;

    @NotNull
    @Valid
    private CareerDTO career;

}