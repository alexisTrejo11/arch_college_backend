package microservice.grade_service.Controller.AcademicHistory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.JWT.JWTSecurity;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.common_classes.DTOs.Grade.GradeDTO;
import microservice.grade_service.Model.AcademicHistory;
import microservice.grade_service.Service.AcademicHistoryService;
import microservice.grade_service.Service.GradeFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Student Academic History API", description = "Endpoints for managing student academic records and grades")
@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
public class StudentAcademicHistoryController {

    private final AcademicHistoryService academicHistoryService;
    private final GradeFinderService gradeFinderService;
    private final JWTSecurity jwtSecurity;

    @Operation(
            summary = "Get Academic History",
            description = "Retrieves complete academic history including all completed courses and grades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Academic history retrieved successfully",
                            content = @Content(schema = @Schema(implementation = AcademicHistory.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Academic history not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/get-my-academic-history")
    public ResponseEntity<ResponseWrapper<AcademicHistory>> getMyAcademicHistory(
            @Parameter(hidden = true) HttpServletRequest request) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        AcademicHistory academicHistory = academicHistoryService.getAcademicHistoryByAccountNumber(accountNumber);

        return ResponseEntity.ok(ResponseWrapper.ok(academicHistory,
                "Academic history retrieved for student " + accountNumber));
    }

    @Operation(
            summary = "Get Annual Grades",
            description = "Retrieves grades organized by academic year",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Annual grades retrieved successfully",
                            content = @Content(schema = @Schema(implementation = GradeDTO.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/get-my-annually-grades")
    public ResponseEntity<ResponseWrapper<List<GradeDTO>>> getMyAnnualGrades(
            @Parameter(hidden = true) HttpServletRequest request) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        List<GradeDTO> annualGrades = gradeFinderService.getAnnuallyGradesByStudentAccountNumber(accountNumber);

        return ResponseEntity.ok(ResponseWrapper.ok(annualGrades,
                annualGrades.size() + " annual grades found for student " + accountNumber));
    }

    @Operation(
            summary = "Get Current Enrollments",
            description = "Retrieves currently enrolled courses and their status",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Current enrollments retrieved successfully",
                            content = @Content(schema = @Schema(implementation = GradeDTO.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/get-my-current-enrollments")
    public ResponseEntity<ResponseWrapper<List<GradeDTO>>> getMyCurrentEnrollments(
            @Parameter(hidden = true) HttpServletRequest request) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        List<GradeDTO> currentEnrollments = gradeFinderService.getCurrentGradesByStudentAccountNumber(accountNumber);

        return ResponseEntity.ok(ResponseWrapper.ok(currentEnrollments,
                currentEnrollments.size() + " current enrollments found for student " + accountNumber));
    }
}