package microservice.enrollment_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Enrollment.EnrollmentDTO;
import microservice.common_classes.DTOs.Enrollment.EnrollmentInsertDTO;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.Schedule.AcademicData;
import microservice.enrollment_service.DTOs.EnrollmentRelationship;
import microservice.enrollment_service.Model.Preload.Group;
import microservice.enrollment_service.Service.EnrollmentCommandService;
import microservice.enrollment_service.Service.EnrollmentRelationshipService;
import microservice.enrollment_service.Service.EnrollmentValidationService;
import microservice.enrollment_service.Service.Implementation.EnrollmentLockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/api/group-enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment Manager", description = "APIs for managing student enrollments in groups")
public class EnrollmentCommandController {

    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentLockService lockService;
    private final EnrollmentRelationshipService enrollmentRelationshipService;
    private final EnrollmentValidationService enrollmentValidationService;

    @Operation(
            summary = "Create a new subject enrollment",
            description = "Creates a new enrollment entry for a student in a specific group. Validates group availability and student eligibility."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Enrollment successfully created",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or group does not exist",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Group is full or student is not eligible",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User not authorized",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User lacks of authority",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            )
    })
    @PostMapping("/{studentAccountNumber}")
    public ResponseEntity<ResponseWrapper<EnrollmentDTO>> createSubjectEnrollment(
            @Parameter(description = "Enrollment details", required = true)
            @Valid @RequestBody EnrollmentInsertDTO enrollmentInsertDTO,

            @Parameter(description = "Student's account number", required = true)
            @PathVariable String studentAccountNumber) {

        Result<Group> groupResult = enrollmentRelationshipService.validateExistingGroup(enrollmentInsertDTO);
        if (!groupResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.conflict(groupResult.getErrorMessage()));
        }

        EnrollmentRelationship enrollmentRelationship = enrollmentRelationshipService.getRelationshipData(
                groupResult.getData(),
                studentAccountNumber);

        Result<Void> validationResult = enrollmentValidationService.validateEnrollment(
                enrollmentInsertDTO,
                enrollmentRelationship,
                studentAccountNumber);

        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(validationResult.getErrorMessage()));
        }

        Result<Void> spotResult = enrollmentRelationshipService.takeSpot(enrollmentRelationship.getGroup().getId());
        if (!spotResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(spotResult.getErrorMessage()));
        }

        enrollmentCommandService.createEnrollment(enrollmentRelationship, enrollmentInsertDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created("Group Enrollment successfully created"));
    }

    @Operation(
            summary = "Delete subject enrollment",
            description = "Removes a student's enrollment from a group by enrollment ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment successfully deleted",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User not authorized",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User lacks of authority",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            )
    })
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteEnrollmentById(
            @Parameter(description = "ID of the enrollment to delete", required = true)
            @PathVariable Long enrollmentId) {
        enrollmentCommandService.deleteEnrollment(enrollmentId);
        return ResponseEntity.ok(ResponseWrapper.deleted("Enrollment"));
    }

    @Operation(
            summary = "Get enrollment lock date",
            description = "Retrieves the current enrollment period's lock date and school period information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lock date successfully retrieved",
                    content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
            )
    })
    @GetMapping("/lock-date")
    public ResponseEntity<ResponseWrapper<LocalDateTime>> getEnrollmentLockDate() {
        LocalDateTime lockDate = lockService.getLockDate();
        String currentSchoolPeriod = AcademicData.getCurrentSchoolPeriod();

        return ResponseEntity.ok(ResponseWrapper.ok(lockDate, String.format("Semester: %s Enrollment lock date: %s", currentSchoolPeriod, lockDate)));
    }
}