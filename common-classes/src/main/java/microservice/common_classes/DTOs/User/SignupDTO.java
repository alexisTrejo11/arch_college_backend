package microservice.common_classes.DTOs.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupDTO {

    @Schema(
            description = "The account number of the user",
            example = "123456789"
    )
    @JsonProperty("account_number")
    @NotNull(message = "account_number can't be null")
    @NotBlank(message = "account_number can't be blank")
    private String accountNumber;

    @Schema(
            description = "The email address of the user",
            example = "user@example.com"
    )
    @JsonProperty("email")
    @NotNull(message = "email can't be null")
    @NotBlank(message = "email can't be blank")
    private String email;

    @Schema(
            description = "The phone number of the user",
            example = "+1234567890"
    )
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(
            description = "The password for the user's account",
            example = "SecurePassword123!"
    )
    @JsonProperty("password")
    @NotNull(message = "password can't be null")
    @NotBlank(message = "password can't be blank")
    private String password;
}