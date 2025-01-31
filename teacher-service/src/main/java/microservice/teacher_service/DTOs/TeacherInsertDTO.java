package microservice.teacher_service.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.common_classes.Utils.Teacher.Title;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TeacherInsertDTO {

    @NotNull(message = "title is obligatory")
    @JsonProperty("title")
    @Enumerated(EnumType.STRING)
    @Schema(description = "The title of the teacher. (e.g. Dr., Prof.)", example = "Dr.")
    private Title title;

    @NotNull(message = "first_name is obligatory")
    @NotBlank(message = "first_name can't be blank")
    @Size(min = 3, message = "first_name must have at least 3 characters")
    @JsonProperty("first_name")
    @Schema(description = "The first name of the teacher.",  example = "John")
    private String firstName;

    @NotNull(message = "last_name is obligatory")
    @NotBlank(message = "last_name can't be blank")
    @Size(min = 3, message = "last_name must have at least 3 characters")
    @JsonProperty("last_name")
    @Schema(description = "The last name of the teacher.",  example = "Doe")
    private String lastName;

    @Pattern(regexp = "^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]\\d$", message = "CURP format is invalid")
    @Size(min = 18, max = 18, message = "CURP must be 18 characters long")
    @JsonProperty("curp")
    @Schema(description = "The CURP (Unique Population Registration Code) of the teacher.", example = "ABCD123456HMNRZX09")
    private String curp;

    @JsonProperty("rfc")
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "RFC must have the correct format of 13 characters")
    @Schema(description = "The RFC (Federal Taxpayer Registry) of the teacher.", example = "XEXX010101000")
    private String rfc;

    @JsonProperty("nss")
    @Pattern(regexp = "^\\d{11}$", message = "Social security number must be 11 digits")
    @Schema(description = "The teacher's social security number.", example = "12345678901")
    private String nss;

    @JsonProperty("date_of_birth")
    @NotNull(message = "date_of_birth is obligatory")
    @Past(message = "date_of_birth must be a past date")
    @Schema(description = "The teacher's date of birth.", example = "1980-05-15T00:00:00")
    private LocalDateTime dateOfBirth;

    @NotNull(message = "speciality is obligatory")
    @NotBlank(message = "speciality can't be blank")
    @Size(min = 3, message = "speciality must have at least 3 characters")
    @JsonProperty("speciality")
    @Schema(description = "The teacher's speciality (e.g. Mathematics, Physics).", example = "Mathematics")
    private String speciality;

    @JsonProperty("hired_at")
    @NotNull(message = "hired_at is obligatory")
    @Schema(description = "The date the teacher was hired.",  example = "2021-06-01T00:00:00")
    private LocalDateTime hired_at;

    @JsonProperty("salary_per_month")
    @NotNull(message = "salary_per_month is obligatory")
    @Positive(message = "salary_per_month must be a positive number")
    @Schema(description = "The teacher's monthly salary.", example = "3000.50")
    private double salaryPerMonth;
}
