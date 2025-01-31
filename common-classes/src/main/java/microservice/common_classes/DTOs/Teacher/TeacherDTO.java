package microservice.common_classes.DTOs.Teacher;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.common_classes.Utils.Teacher.Title;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(name = "TeacherDTO", description = "Data Transfer Object for Teacher entity")
public class TeacherDTO {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the teacher", example = "1")
    private Long id;

    @JsonProperty("title")
    @Schema(description = "Title of the teacher", example = "PROFESSOR")
    private Title Title;

    @JsonProperty("account_number")
    @Schema(description = "Teacher's account number", example = "1234567890")
    private String accountNumber;

    @JsonProperty("speciality")
    @Schema(description = "Specialization of the teacher", example = "Mathematics")
    private String speciality;

    @JsonProperty("hired_at")
    @Schema(description = "Date when the teacher was hired", example = "2023-06-15T10:00:00")
    private LocalDateTime hired_at;

    @JsonProperty("salary_per_month")
    @Schema(description = "Monthly salary of the teacher", example = "5000.00")
    private double salaryPerMonth;

    @JsonProperty("first_name")
    @Schema(description = "Teacher's first name", example = "John")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Teacher's last name", example = "Doe")
    private String lastName;

    @JsonProperty("date_of_birth")
    @Schema(description = "Teacher's date of birth", example = "1985-08-20T00:00:00")
    private LocalDateTime dateOfBirth;

    @JsonProperty("curp")
    @Schema(description = "Unique Population Registry Code (CURP)", example = "JDOE850820HDFRNR07")
    private String curp;

    @JsonProperty("rfc")
    @Schema(description = "Federal Taxpayer Registry (RFC)", example = "JDOE850820ABC")
    private String rfc;

    @JsonProperty("nss")
    @Schema(description = "Social Security Number (NSS)", example = "12345678901")
    private String nss;
}
