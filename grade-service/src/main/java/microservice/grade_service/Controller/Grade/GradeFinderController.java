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
import microservice.common_classes.Utils.SubjectType;
import microservice.grade_service.Service.GradeFinderService;
import microservice.grade_service.Utils.Credits.GradeFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Grades API", description = "Endpoints for retrieving and searching grade information")
@RestController
@RequestMapping("/v1/api/grades")
@RequiredArgsConstructor
public class GradeFinderController {

    private final GradeFinderService gradeFinderService;

    @Operation(
            summary = "Get Grade by ID",
            description = "Retrieves detailed information about a specific grade using its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grade found successfully",
                            content = @Content(schema = @Schema(implementation = GradeDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grade not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/{gradeId}")
    public ResponseEntity<ResponseWrapper<GradeDTO>> getGradeById(
            @Parameter(description = "Unique numeric ID of the grade", required = true, example = "12345")
            @PathVariable Long gradeId) {

        Result<GradeDTO> gradeResult = gradeFinderService.getGradeById(gradeId);

        if (!gradeResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseWrapper.notFound(gradeResult.getErrorMessage()));
        }

        return ResponseEntity.ok(ResponseWrapper.ok(gradeResult.getData(), "Grade data successfully retrieved"));
    }

    @Operation(
            summary = "Search Grades with Filters",
            description = "Retrieves a paginated list of grades based on various search criteria including student account, period, subject, and type",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grades retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<Page<GradeDTO>>> findGrades(
            @Parameter(description = "Student account number filter", example = "A12345678")
            @RequestParam(required = false) String accountNumber,

            @Parameter(description = "School period filter (format: YYYY-T)", example = "2024-1")
            @RequestParam(required = false) String schoolPeriod,

            @Parameter(description = "Subject ID filter", example = "101")
            @RequestParam(required = false) Long subjectId,

            @Parameter(description = "Subject type filter", schema = @Schema(implementation = SubjectType.class))
            @RequestParam(required = false) SubjectType subjectType,

            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort criteria (format: property,direction)", example = "id,asc")
            @RequestParam(defaultValue = "id,asc") String sort) {

        GradeFinderFilter gradeFilter = new GradeFinderFilter(accountNumber, schoolPeriod, subjectId, subjectType);
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));

        Page<GradeDTO> grades = gradeFinderService.getGradesByFilters(gradeFilter, pageable);
        return ResponseEntity.ok(ResponseWrapper.ok(grades,
                grades.getNumberOfElements() + " grades found using filters: " + gradeFilter.getActiveFilters()));
    }

    @Operation(
            summary = "Get Pending Validation Grades",
            description = "Retrieves a paginated list of grades that require validation or approval",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pending grades retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    @GetMapping("/pending-validation")
    public ResponseEntity<ResponseWrapper<Page<GradeDTO>>> getPendingValidationGrades(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort criteria (format: property,direction)", example = "id,asc")
            @RequestParam(defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        Page<GradeDTO> gradePage = gradeFinderService.getPendingValidationGrades(pageable);

        return ResponseEntity.ok(ResponseWrapper.ok(gradePage,
                gradePage.getNumberOfElements() + " grades pending validation found"));
    }

    private Sort parseSortString(String sort) {
        List<String> allowedProperties = List.of("id", "studentAccountNumber", "schoolPeriod", "subjectId", "subjectType");
        String[] sortParams = sort.split(",");

        if (sortParams.length == 2) {
            String property = sortParams[0].trim();
            String direction = sortParams[1].trim();

            if (allowedProperties.contains(property)) {
                return direction.equalsIgnoreCase("desc")
                        ? Sort.by(Sort.Order.desc(property))
                        : Sort.by(Sort.Order.asc(property));
            }
        }

        return Sort.by(Sort.Order.asc("id"));
    }
}