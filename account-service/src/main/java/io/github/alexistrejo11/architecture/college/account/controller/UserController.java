package io.github.alexistrejo11.architecture.college.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.User.ProfileDTO;
import io.github.alexistrejo11.architecture.college.common.config.jwt.JWTSecurity;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.account.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/user")
@RequiredArgsConstructor
@Tag(name = "User Profile Manager", description = "Endpoints for managing user-related operations")
public class UserController {

    private final JWTSecurity jwtSecurity;
    private final UserService userService;

    @Operation(
            summary = "Get my profile",
            description = "Retrieves the profile information of the authenticated user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing JWT token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Profile not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @GetMapping("/my-profile")
    public ResponseEntity<ResponseWrapper<ProfileDTO>> getMyProfile(HttpServletRequest request) {
        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);

        ProfileDTO profileDTO = userService.getProfileDataByUsername(accountNumber);

        return ResponseEntity.ok(ResponseWrapper.found(profileDTO, "User Profile"));
    }
}