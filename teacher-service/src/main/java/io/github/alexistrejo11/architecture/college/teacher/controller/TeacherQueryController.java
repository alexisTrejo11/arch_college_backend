package io.github.alexistrejo11.architecture.college.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.teacher.service.TeacherQueryService;
import io.github.alexistrejo11.architecture.college.teacher.config.SwaggerExamples;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Manager", description = "API for managing teacher data")
public class TeacherQueryController {

    private final TeacherQueryService teacherQueryService;

    @Operation(summary = "Get teacher by ID", description = "Fetches a teacher by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher data successfully fetched",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_NOT_FOUND_RESPONSE)))
    })
    @GetMapping("/{teacherId}")
    public ResponseEntity<ResponseWrapper<TeacherDTO>> getTeacherById(@PathVariable Long teacherId) {
        return teacherQueryService.getTeacherById(teacherId)
                .map(teacher -> ResponseEntity.ok(ResponseWrapper.ok(teacher, "Teacher data successfully fetched")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Teacher not found")));
    }

    @Operation(summary = "Get teacher by account number", description = "Fetches a teacher by their account number.")
    @GetMapping("/by-accountNumber/{accountNumber}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher data successfully fetched",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_NOT_FOUND_RESPONSE)))
    })
    public ResponseEntity<ResponseWrapper<TeacherDTO>> getTeacherByAccountNumber(@PathVariable String accountNumber) {
        return teacherQueryService.getTeacherByAccountNumber(accountNumber)
                .map(teacher -> ResponseEntity.ok(ResponseWrapper.ok(teacher, "Teacher data successfully fetched")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Teacher not found")));
    }

    @Operation(summary = "Get teachers by IDs", description = "Fetches multiple teachers by their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher data successfully fetched",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_RESPONSE))),
    })
    @GetMapping("/by-ids")
    public ResponseEntity<ResponseWrapper<List<TeacherDTO>>> getTeachersByIds(@RequestParam Set<Long> idSet) {
        List<TeacherDTO> teachers = teacherQueryService.getTeachersByIds(idSet);
        return ResponseEntity.ok(ResponseWrapper.ok(teachers, "Teachers data successfully fetched"));
    }
}
