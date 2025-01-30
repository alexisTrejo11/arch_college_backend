package microservice.user_service.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponseDTO {
    private String refreshToken;
    private String accessToken;
}
