package io.github.alexistrejo11.architecture.college.account.controller.dto;

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
