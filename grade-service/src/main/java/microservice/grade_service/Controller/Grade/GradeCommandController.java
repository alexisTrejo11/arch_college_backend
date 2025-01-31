package microservice.grade_service.Controller.Grade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Grade.GradeDTO;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.common_classes.Utils.Response.Result;
import microservice.grade_service.Service.GradeCommandService;
import microservice.grade_service.Service.GradeValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Grades API", description = "Endpoints for managing grade operations")
@RestController
@RequestMapping("/v1/api/grades")
@RequiredArgsConstructor
public class GradeCommandController {

    private final GradeCommandService gradeCommandService;
    private final GradeValidationService gradeValidationService;

    @Operation(
            summary = "Authorize Grade",
            description = "Validates and authorizes an existing grade, making it part of the student's official grades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grade successfully authorized",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Grade cannot be authorized",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grade not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/{gradeId}/authorize")
    public ResponseEntity<ResponseWrapper<GradeDTO>> authorizeGrade(
            @Parameter(description = "ID of the grade to authorize", required = true, example = "12345")
            @PathVariable Long gradeId) {

        Result<Void> validationResult = gradeValidationService.authorizeGradeById(gradeId);

        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseWrapper.badRequest(validationResult.getErrorMessage()));
        }

        return ResponseEntity.ok(ResponseWrapper.ok("Grade with ID " + gradeId + " successfully authorized"));
    }

    @Operation(
            summary = "Delete Grade",
            description = "Performs a soft delete of a grade by its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grade successfully deleted",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grade not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<ResponseWrapper<Void>> softDeleteGradeById(
            @Parameter(description = "ID of the grade to delete", required = true, example = "12345")
            @PathVariable Long gradeId) {

        gradeCommandService.deleteGradeById(gradeId);
        return ResponseEntity.ok(ResponseWrapper.ok("Grade with ID " + gradeId + " successfully deleted"));
    }
}