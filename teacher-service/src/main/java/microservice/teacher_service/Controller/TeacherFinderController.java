package microservice.teacher_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Teacher.TeacherDTO;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.teacher_service.Service.TeacherFinderService;
import microservice.teacher_service.DTOs.SwaggerExamples;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Manager", description = "API for managing teacher data")
public class TeacherFinderController {

    private final TeacherFinderService teacherFinderService;

    @Operation(summary = "Get teacher by ID", description = "Fetches a teacher by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher data successfully fetched",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TEACHER_NOT_FOUND_RESPONSE)))
    })
    @GetMapping("/{teacherId}")
    public ResponseEntity<ResponseWrapper<TeacherDTO>> getTeacherById(@PathVariable Long teacherId) {
        return teacherFinderService.getTeacherById(teacherId)
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
        return teacherFinderService.getTeacherByAccountNumber(accountNumber)
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
        List<TeacherDTO> teachers = teacherFinderService.getTeachersByIds(idSet);
        return ResponseEntity.ok(ResponseWrapper.ok(teachers, "Teachers data successfully fetched"));
    }
}
