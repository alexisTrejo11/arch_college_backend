package microservice.common_classes.DTOs.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProfileDTO {

    @Schema(
            description = "The email address of the user",
            example = "user@example.com"
    )
    @JsonProperty("email")
    private String email;

    @Schema(
            description = "The phone number of the user",
            example = "+1234567890"
    )
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(
            description = "The username of the user",
            example = "john_doe"
    )
    @JsonProperty("username")
    private String username;

    @Schema(
            description = "The main role of the user (e.g., STUDENT, TEACHER, ADMIN)",
            example = "STUDENT"
    )
    @JsonProperty("main_role")
    private String mainRole;

    @Schema(
            description = "The first name of the user",
            example = "John"
    )
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "The last name of the user",
            example = "Doe"
    )
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "The date of birth of the user",
            example = "1990-01-01T00:00:00"
    )
    @JsonProperty("date_of_birth")
    private LocalDateTime dateOfBirth;
}