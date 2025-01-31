package microservice.user_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.common_classes.DTOs.User.LoginDTO;
import microservice.common_classes.DTOs.User.SignupDTO;
import microservice.common_classes.DTOs.User.UserDTO;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.common_classes.Utils.Response.Result;
import microservice.user_service.Service.AuthService;
import microservice.user_service.Service.UserService;
import microservice.user_service.Utils.JWTResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(
            summary = "Sign up as a student",
            description = "Registers a new student account with the provided details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Student registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict with existing credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @PostMapping("/signup/student")
    public ResponseEntity<ResponseWrapper<JWTResponseDTO>> signupStudent(@Valid @RequestBody SignupDTO signupDTO) {
        log.info("Requesting user creation for student accountNumber: [{}] ", signupDTO.getAccountNumber());

        Result<Void> validateStudentResult = authService.validateStudent(signupDTO.getAccountNumber());
        if (!validateStudentResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(validateStudentResult.getErrorMessage()));
        }

        Result<Void> passwordFormatResult = authService.validatePasswordFormat(signupDTO.getPassword());
        if (!passwordFormatResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(passwordFormatResult.getErrorMessage()));
        }

        Result<Void> credentialsResult = authService.validateSignupCredentials(signupDTO);
        if (!credentialsResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(credentialsResult.getErrorMessage()));
        }

        UserDTO userDTO = userService.createUser(signupDTO, "STUDENT");
        userService.addMemberRelationAsync(userDTO.getUsername());

        JWTResponseDTO jwtToken = authService.proccesSingup(userDTO);

        log.info("Student with accountNumber [{}] successfully signed up", signupDTO.getAccountNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(jwtToken, "User"));
    }

    @Operation(
            summary = "Sign up as an admin",
            description = "Registers a new admin account with the provided details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Admin registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict with existing credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @PostMapping("/signup/admin")
    public ResponseEntity<ResponseWrapper<JWTResponseDTO>> signupAdmin(@Valid @RequestBody SignupDTO signupDTO) {
        Result<Void> passwordFormatResult = authService.validatePasswordFormat(signupDTO.getPassword());
        if (!passwordFormatResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(passwordFormatResult.getErrorMessage()));
        }

        Result<Void> credentialsResult = authService.validateSignupCredentials(signupDTO);
        if (!credentialsResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(credentialsResult.getErrorMessage()));
        }

        UserDTO userDTO = userService.createUser(signupDTO, "ADMIN");
        userService.addMemberRelationAsync(userDTO.getUsername());

        JWTResponseDTO jwtToken = authService.proccesSingup(userDTO);
        log.info("Admin with username [{}] successfully signed up", signupDTO.getAccountNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(jwtToken, "User"));
    }

    @Operation(
            summary = "Sign up as a teacher",
            description = "Registers a new teacher account with the provided details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Teacher registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict with existing credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @PostMapping("/signup/teacher")
    public ResponseEntity<ResponseWrapper<JWTResponseDTO>> signupTeacher(@Valid @RequestBody SignupDTO signupDTO) {
        log.info("Requesting user creation for teacher accountNumber: [{}] ", signupDTO.getAccountNumber());

        Result<Void> validateStudentResult = authService.validateTeacher(signupDTO.getAccountNumber());
        if (!validateStudentResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(validateStudentResult.getErrorMessage()));
        }

        Result<Void> passwordFormatResult = authService.validatePasswordFormat(signupDTO.getPassword());
        if (!passwordFormatResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(passwordFormatResult.getErrorMessage()));
        }

        Result<Void> validationResult = authService.validateSignupCredentials(signupDTO);
        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(validationResult.getErrorMessage()));
        }

        UserDTO userDTO = userService.createUser(signupDTO, "TEACHER");
        userService.addMemberRelationAsync(userDTO.getUsername());

        JWTResponseDTO jwtToken = authService.proccesSingup(userDTO);

        log.info("Teacher with accountNumber [{}] successfully signed up", signupDTO.getAccountNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(jwtToken, "User"));
    }

    @Operation(
            summary = "Log in",
            description = "Authenticates a user with the provided credentials and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict during login validation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<JWTResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        Result<UserDTO> validationResult = authService.validateLoginCredentials(loginDTO);
        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(validationResult.getErrorMessage()));
        }

        UserDTO userDTO = validationResult.getData();
        JWTResponseDTO jwtToken = authService.processLogin(userDTO);

        return ResponseEntity.ok(ResponseWrapper.ok(jwtToken, "Login successfully completed"));
    }
}