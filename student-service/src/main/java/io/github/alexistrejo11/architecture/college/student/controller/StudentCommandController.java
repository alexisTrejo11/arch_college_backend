package io.github.alexistrejo11.architecture.college.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentInsertDTO;
import io.github.alexistrejo11.architecture.college.common.models.subject.ProfessionalLineModality;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.student.config.openapi.ApiResponseExamples;
import io.github.alexistrejo11.architecture.college.student.service.StudentRelationService;
import io.github.alexistrejo11.architecture.college.student.service.StudentCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Manager", description = "API for managing student data")
public class StudentCommandController {

    private final StudentCommandService studentCommandService;
    private final StudentRelationService studentRelationService;

    @Operation(
            summary = "Create a new student",
            description = "Creates a new student entry.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student successfully created",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.STUDENT_CREATED))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.INVALID_INPUT_DATA))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.UNAUTHORIZED))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User lacks required permissions",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.FORBIDDEN)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> createStudent(@Valid @RequestBody StudentInsertDTO studentInsertDTO) {
        Result<Void> careerResult = studentRelationService.validateExistingCareerId(studentInsertDTO.getCareerId());
        if (!careerResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(careerResult.getErrorMessage()));
        }

        StudentDTO studentDTO = studentCommandService.createStudent(studentInsertDTO);

        studentRelationService.initAcademicHistoryAsync(studentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created("Student successfully created"));
    }

    @Operation(
            summary = "Update student data",
            description = "Updates the personal data of an existing student.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student successfully updated",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.STUDENT_UPDATED))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.STUDENT_NOT_FOUND))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.UNAUTHORIZED))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User lacks required permissions",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.FORBIDDEN)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{studentId}")
    public ResponseEntity<ResponseWrapper<Void>> updatePersonalStudentData(@Valid @RequestBody StudentInsertDTO studentInsertDTO,
                                                                           @PathVariable Long studentId) {
        studentCommandService.updateStudent(studentInsertDTO, studentId);
        return ResponseEntity.ok(ResponseWrapper.ok(null, "Student successfully updated"));
    }

    @Operation(
            summary = "Delete student by ID",
            description = "Deletes a student by their ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student successfully deleted",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.STUDENT_DELETED))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.STUDENT_NOT_FOUND))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.UNAUTHORIZED))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User lacks required permissions",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.FORBIDDEN)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResponseWrapper<StudentDTO>> deleteStudentById(@PathVariable Long studentId) {
        studentCommandService.deleteStudent(studentId);
        return ResponseEntity.ok(ResponseWrapper.ok(null, "Student successfully deleted"));
    }


    @Operation(
            summary = "Set professional line data",
            description = "Assigns a professional line and modality to a student.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Professional line data set successfully",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.PROFESSIONAL_LINE_SET)))
            }
    )
    @PostMapping("/{studentAccount}/set-professionalLine/{professionalLineId}/modality/{professionalLineModality}")
    public ResponseEntity<Void> setProfessionalLineData(@Valid @PathVariable String studentAccount,
                                                        @PathVariable ProfessionalLineModality professionalLineModality,
                                                        @PathVariable Long professionalLineId) {
        studentCommandService.setProfessionalLineData(studentAccount, professionalLineId, professionalLineModality);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Increase semester completed",
            description = "Increases the semester count of a student.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Semester count successfully increased",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ApiResponseExamples.SEMESTER_INCREASED)))
            }
    )
    @PostMapping("/{studentAccount}/increase-semester-completed")
    public ResponseEntity<Void> increaseSemesterCompleted(@Valid @PathVariable String studentAccount) {
        studentCommandService.increaseSemestersCursed(studentAccount);
        return ResponseEntity.ok().build();
    }
}
