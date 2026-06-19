package io.github.alexistrejo11.architecture.college.common.dto.Enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentDTO {

    @Schema(description = "Unique identifier of the enrollment", example = "1")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Key identifying the group associated with the enrollment", example = "1203")
    @JsonProperty("group_key")
    private String groupKey;

    @Schema(description = "Key identifying the subject associated with the enrollment", example = "1107")
    @JsonProperty("subject_key")
    private String subjectKey;

    @Schema(description = "School period during which the enrollment is active", example = "2025-2")
    @JsonProperty("subject_period")
    private String schoolPeriod;

    @Schema(description = "Account number of the student enrolled", example = "315297104")
    @JsonProperty("student_account_number")
    private String studentAccountNumber;
}