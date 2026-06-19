package io.github.alexistrejo11.architecture.college.enrollment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.config.jwt.JWTSecurity;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.enrollment.service.dto.EnrollmentRelationship;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Group;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentCommandService;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentRelationshipService;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentFinderService;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/group-enrollments/students")
@RequiredArgsConstructor
@Tag(name = "Student Enrollment Manager", description = "Endpoints for managing student group enrollments")
public class StudentEnrollmentController {

    private final EnrollmentFinderService enrollmentFinderService;
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentValidationService enrollmentValidationService;
    private final EnrollmentRelationshipService enrollmentRelationshipService;
    private final JWTSecurity jwtSecurity;

    @Operation(
            summary = "Get current student's enrollments",
            description = "Retrieves all active enrollments for the authenticated student",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollments successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired JWT token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "JWT token missing or invalid format",
                    content = @Content
            )
    })
    @GetMapping("/my-enrollments")
    public ResponseEntity<ResponseWrapper<List<EnrollmentDTO>>> getMyEnrollments(HttpServletRequest request) {
        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);

        List<EnrollmentDTO> enrollments = enrollmentFinderService.getByAccountNumber(accountNumber);

        return ResponseEntity.ok(ResponseWrapper.found(enrollments, "Enrollments"));
    }

    @Operation(
            summary = "Student new enrollment",
            description = "Enrolls the authenticated student in a specific group",
            security = @SecurityRequirement(name = "bearerAuth") // Aplica la autenticación Bearer
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Enrollment successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid enrollment data or group does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired JWT token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Student not eligible or group is full",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> makeAnEnrollment(@Valid @RequestBody EnrollmentInsertDTO enrollmentInsertDTO,
                                                                  HttpServletRequest request) {
        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);

        Result<Group> groupResult = enrollmentRelationshipService.validateExistingGroup(enrollmentInsertDTO);
        if (!groupResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.conflict(groupResult.getErrorMessage()));
        }

        EnrollmentRelationship enrollmentRelationship = enrollmentRelationshipService.getRelationshipData(groupResult.getData(), accountNumber);

        Result<Void> validationResult = enrollmentValidationService.validateEnrollment(enrollmentInsertDTO, enrollmentRelationship, accountNumber);
        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(validationResult.getErrorMessage()));
        }

        enrollmentCommandService.createEnrollment(enrollmentRelationship, enrollmentInsertDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created("Group Enrollment successfully created"));
    }

    @Operation(
            summary = "Delete student enrollment",
            description = "Removes the authenticated student's enrollment from a specific group and subject",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment successfully deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired JWT token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found for the given group and subject",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class)
                    )
            )
    })
    @DeleteMapping("/{groupKey}/{subjectKey}")
    public ResponseEntity<ResponseWrapper<Void>> deleteAnEnrollment(@Valid @PathVariable String groupKey,
                                                                    @PathVariable String subjectKey,
                                                                    HttpServletRequest request) {
        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);

        Result<Void> deleteResult = enrollmentCommandService.deleteEnrollment(groupKey, subjectKey, accountNumber);
        if (!deleteResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.badRequest(deleteResult.getErrorMessage()));
        }

        return ResponseEntity.ok().body(ResponseWrapper.deleted("Enrollment"));
    }
}