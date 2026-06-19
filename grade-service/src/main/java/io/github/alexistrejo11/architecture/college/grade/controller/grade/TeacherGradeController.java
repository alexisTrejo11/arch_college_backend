package io.github.alexistrejo11.architecture.college.grade.controller.grade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.config.jwt.JWTSecurity;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GroupDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.TeacherQualificationDTO;
import io.github.alexistrejo11.architecture.college.grade.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teacher Grades API", description = "Endpoints for managing teacher grading operations")
@RestController
@RequestMapping("/v1/api/teachers/grades/groups")
@RequiredArgsConstructor
@Slf4j
public class TeacherGradeController {

    private final GroupService groupService;
    private final JWTSecurity jwtSecurity;

    @Operation(
            summary = "Get Pending Groups for Grading",
            description = "Retrieves a list of groups where the teacher has pending grades to assign",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pending groups retrieved successfully",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/pending-grade")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getMyGroupsToBeQualified(
            @Parameter(hidden = true) HttpServletRequest request) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        List<GroupDTO> groups = groupService.getTeacherGroupsPendingToBeQualified(accountNumber);

        log.info("Retrieved {} pending groups for teacher {}", groups.size(), accountNumber);
        return ResponseEntity.ok(ResponseWrapper.ok(groups,
                groups.size() + " groups pending grading found for teacher " + accountNumber));
    }

    @Operation(
            summary = "Get Grading History",
            description = "Retrieves a list of groups where the teacher has completed grading",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grading history retrieved successfully",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/my-history")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getMyGroupsGradedHistory(
            @Parameter(hidden = true) HttpServletRequest request) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        List<GroupDTO> groups = groupService.getTeacherGroupsQualified(accountNumber);

        log.info("Retrieved {} graded groups for teacher {}", groups.size(), accountNumber);
        return ResponseEntity.ok(ResponseWrapper.ok(groups,
                groups.size() + " graded groups found for teacher " + accountNumber));
    }

    @Operation(
            summary = "Assign Grades to Group",
            description = "Allows a teacher to assign grades to students in a specific group",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grades assigned successfully",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid grading request",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/set-grades")
    public ResponseEntity<ResponseWrapper<Void>> putGradeValuesToGroup(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(description = "Grade assignment details", required = true,
                    content = @Content(schema = @Schema(implementation = TeacherQualificationDTO.class)))
            @Valid @RequestBody TeacherQualificationDTO teacherQualificationDTO) {

        String accountNumber = jwtSecurity.getAccountNumberFromToken(request);
        log.info("Teacher {} initiating grade assignment for group {}", accountNumber, teacherQualificationDTO.getGroupId());

        Result<Void> periodValidation = groupService.validateGroupGradingPeriodTime();
        if (!periodValidation.isSuccess()) {
            log.warn("Grade assignment failed for teacher {}: {}", accountNumber, periodValidation.getErrorMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseWrapper.badRequest(periodValidation.getErrorMessage()));
        }

        Result<Void> qualificationValidation = groupService.validateGroupQualification(teacherQualificationDTO, accountNumber);
        if (!qualificationValidation.isSuccess()) {
            log.warn("Grade assignment validation failed for teacher {}: {}", accountNumber, qualificationValidation.getErrorMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseWrapper.badRequest(qualificationValidation.getErrorMessage()));
        }

        groupService.addGroupQualifications(teacherQualificationDTO, accountNumber);
        groupService.addGradesToAcademicHistoryAsync(teacherQualificationDTO.getGroupId());

        log.info("Teacher {} successfully assigned grades to group {}", accountNumber, teacherQualificationDTO.getGroupId());
        return ResponseEntity.ok(ResponseWrapper.ok("Grades successfully assigned to group " + teacherQualificationDTO.getGroupId()));
    }
}