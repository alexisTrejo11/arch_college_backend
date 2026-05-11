package io.github.alexistrejo11.architecture.college.enrollment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.enrollment.service.EnrollmentFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/group-enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment Manager", description = "Endpoints for fetching enrollment data")
public class EnrollmentFinderController {

    private final EnrollmentFinderService enrollmentFinderService;

    @GetMapping("/{enrollmentId}")
    @Operation(
            summary = "Get enrollment by ID",
            description = "Retrieves detailed information about a specific enrollment using its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment data successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found with the given ID",
                    content = @Content
            )
    })
    public ResponseEntity<ResponseWrapper<EnrollmentDTO>> getEnrollmentById(@PathVariable Long enrollmentId) {
        Optional<EnrollmentDTO> enrollment = enrollmentFinderService.getById(enrollmentId);
        return enrollment.map(enrollmentDTO -> ResponseEntity.ok(ResponseWrapper.ok(enrollmentDTO, "Enrollment data successfully fetched")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Enrollment not found")));

    }

    @GetMapping("/by-student/{studentAccountNumber}")
    @Operation(summary = "Get enrollments by student account number", description = "Fetches all enrollments for a given student account number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enrollments successfully fetched"),
            @ApiResponse(responseCode = "404", description = "No enrollments found for the given student")
    })
    public ResponseEntity<ResponseWrapper<List<EnrollmentDTO>>> getEnrollmentsByAccountNumber(@Valid @PathVariable String studentAccountNumber) {
        List<EnrollmentDTO> enrollments = enrollmentFinderService.getByAccountNumber(studentAccountNumber);
        return ResponseEntity.ok(ResponseWrapper.ok(enrollments, "GroupEnrollment data successfully fetched"));
    }
}
