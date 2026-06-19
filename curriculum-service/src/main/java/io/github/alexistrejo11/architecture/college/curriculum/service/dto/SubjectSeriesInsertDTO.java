package io.github.alexistrejo11.architecture.college.curriculum.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSeriesInsertDTO {
    @NotNull(message = "name can't be null")
    @NotBlank(message = "name can't be blank")
    private String name;

    @NotNull(message = "subjectsIds can't be null")
    private Set<Long> subjectsIds;

    @NotNull(message = "subject_type can't be null")
    private SubjectType subjectType;

}
