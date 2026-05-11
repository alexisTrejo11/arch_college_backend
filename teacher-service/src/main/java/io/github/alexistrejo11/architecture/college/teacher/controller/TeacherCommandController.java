package io.github.alexistrejo11.architecture.college.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.teacher.service.dto.TeacherInsertDTO;
import io.github.alexistrejo11.architecture.college.teacher.service.TeacherCommandService;
import io.github.alexistrejo11.architecture.college.teacher.config.SwaggerExamples;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Manager", description = "API for managing teacher data")
public class TeacherCommandController {

    private final TeacherCommandService teacherCommandService;

    @Operation(
            summary = "Create a new teacher . **Requires Admin Role**",
            description = "Creates a new teacher entry.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Teacher successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.TEACHER_CREATED_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data validation",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.INVALID_DATA_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authorization is missing or invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.UNAUTHROZED)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User does not have sufficient permissions",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.FORBIDDEN)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> createTeacher(@Valid @RequestBody TeacherInsertDTO teacherInsertDTO) {
        teacherCommandService.createTeacher(teacherInsertDTO);
        return ResponseEntity.ok(ResponseWrapper.ok(null, "Teacher successfully created"));
    }

    @Operation(
            summary = "Delete teacher by ID. **Requires Admin Role**",
            description = "Deletes a teacher by their ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Teacher successfully deleted",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.TEACHER_DELETED_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Teacher not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.TEACHER_NOT_FOUND_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authorization is missing or invalid",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User does not have sufficient permissions",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteTeacher(@PathVariable Long teacherId) {
        teacherCommandService.deleteTeacher(teacherId);
        return ResponseEntity.ok(ResponseWrapper.ok(null, "Teacher successfully deleted"));
    }


    @GetMapping("/{teacherAccountNumber}/validate")
    public boolean validateExistingTeacherById(@PathVariable String teacherAccountNumber) {
        return teacherCommandService.validateExistingTeacher(teacherAccountNumber);
    }
}
