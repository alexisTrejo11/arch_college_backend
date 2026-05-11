package io.github.alexistrejo11.architecture.college.common.dto.Enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
public class EnrollmentInsertDTO {

    @Schema(
            description = "Key identifying the group associated with the enrollment",
            example = "1120"
    )
    @JsonProperty("group_key")
    @NotNull(message = "group_key can't be null")
    @Positive(message = "group_key can't be negative")
    private String groupKey;

    @Schema(
            description = "Key identifying the subject associated with the enrollment",
            example = "1205"
    )
    @JsonProperty("subject_key")
    @NotNull(message = "subject_key can't be null")
    @Positive(message = "subject_key can't be negative")
    private String subjectKey;
}