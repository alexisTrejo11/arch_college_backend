package microservice.common_classes.DTOs.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

    @Schema(
            description = "The account number of the user",
            example = "123456789"
    )
    @JsonProperty("account_number")
    private String accountNumber;

    @Schema(
            description = "The password for the user's account",
            example = "SecurePassword123!"
    )
    @JsonProperty("password")
    private String password;
}