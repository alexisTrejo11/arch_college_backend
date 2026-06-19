package io.github.alexistrejo11.architecture.college.grade.controller.academicHistory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.grade.model.AcademicHistory;
import io.github.alexistrejo11.architecture.college.grade.service.AcademicHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/academic-histories")
@RequiredArgsConstructor
@Tag(name = "Academic History", description = "Academic History management APIs")
public class AcademicHistoryController {

    private final AcademicHistoryService academicHistoryService;

    @Operation(
            summary = "Get Academic History by Student Account Number",
            description = "Retrieves a student's academic history using their account number. Requires ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Academic History found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Academic History not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Requires ADMIN role",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account number format",
                    content = @Content
            )
    })
    @PostMapping("/student/{accountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<AcademicHistory>> getAcademicHistoryByStudentAccountNumber(
            @Parameter(description = "Student's account number", required = true)
            @Valid @PathVariable String accountNumber
    ) {
        AcademicHistory academicHistory = academicHistoryService.getAcademicHistoryByAccountNumber(accountNumber);
        return ResponseEntity.ok(ResponseWrapper.found(academicHistory, "Academic History", "account number", accountNumber));
    }
}