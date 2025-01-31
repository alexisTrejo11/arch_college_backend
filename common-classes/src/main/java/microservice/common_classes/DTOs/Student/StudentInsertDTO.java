package microservice.common_classes.DTOs.Student;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StudentInsertDTO {

    @JsonProperty("first_name")
    @NotNull(message = "first_name is obligatory")
    @NotEmpty(message = "first_name can't be empty")
    @Schema(description = "First name of the student", example = "John")
    private String firstName;

    @JsonProperty("last_name")
    @NotNull(message = "last_name is obligatory")
    @NotEmpty(message = "last_name can't be empty")
    @Schema(description = "Last name of the student", example = "Doe")
    private String lastName;

    @JsonProperty("date_of_birth")
    @NotNull(message = "date_of_birth is obligatory")
    @Past(message = "date_of_birth must be on a past date")
    @Schema(description = "Date of birth of the student", example = "2000-01-01T00:00:00")
    private LocalDateTime dateOfBirth;

    @JsonProperty("career_id")
    @NotNull(message = "career_id can't be null")
    @Positive(message = "career_id can't be negative")
    @Schema(description = "ID of the student's career", example = "101")
    private Long careerId;

    @JsonProperty("curp")
    @NotNull(message = "curp can't be null")
    @Pattern(
            regexp = "^[A-Z]{4}\\d{6}[H|M][A-Z]{2}[A-Z0-9]{3}[0-9A-Z]$",
            message = "curp must match the Mexican CURP format"
    )
    @Schema(description = "CURP of the student (Mexican ID format)", example = "ABCD123456HMEXXX01")
    private String curp;
}
