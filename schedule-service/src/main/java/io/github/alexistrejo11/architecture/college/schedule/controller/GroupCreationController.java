package io.github.alexistrejo11.architecture.college.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ElectiveGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ObligatoryGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.schedule.config.documentation.SwaggerExamples;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupRelationshipsDTO;
import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupCreationService;
import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupRelationshipService;
import io.github.alexistrejo11.architecture.college.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Groups Manager", description = "Endpoints for managing groups")
@RestController
@RequestMapping("/v1/api/groups")
@RequiredArgsConstructor
public class GroupCreationController {

    private final GroupCreationService groupCreationService;
    private final GroupRelationshipService groupRelationshipService;
    private final ScheduleService scheduleService;

    @Operation(
            summary = "Create Obligatory Group",
            description = "Create a new obligatory group with the specified details",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Group created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.OBLIGATORY_GROUP_CREATED_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict in group schedule",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.CONFLICT_RESPONSE)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/obligatory")
    public ResponseEntity<ResponseWrapper<GroupDTO>> createObligatoryGroup(
            @Valid @RequestBody ObligatoryGroupInsertDTO obligatoryGroupInsertDTO) {

        Result<GroupRelationshipsDTO> relationshipsResult = groupRelationshipService.validateAndGetRelationships(obligatoryGroupInsertDTO);
        if (!relationshipsResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(relationshipsResult.getErrorMessage()));
        }

        Result<Void> classroomScheduleResult = scheduleService.validateClassroomSchedule(obligatoryGroupInsertDTO.getClassroom(), obligatoryGroupInsertDTO.getSchedule(), null);
        if (!classroomScheduleResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(classroomScheduleResult.getErrorMessage()));
        }

        Result<Void> teacherScheduleResult = scheduleService.validateTeachersSchedule(obligatoryGroupInsertDTO.getTeacherIds(), obligatoryGroupInsertDTO.getSchedule(), null);
        if (!teacherScheduleResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(teacherScheduleResult.getErrorMessage()));
        }

        GroupDTO group = groupCreationService.createGroup(obligatoryGroupInsertDTO, relationshipsResult.getData());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(group,"Group"));
    }

    @Operation(
            summary = "Create Elective Group",
            description = "Create a new elective group with the specified details",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Group created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.ELECTIVE_GROUP_CREATED_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict in group schedule",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.CONFLICT_RESPONSE)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authorization is missing or invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = SwaggerExamples.UNAUTHORIZED)
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/elective")
    public ResponseEntity<ResponseWrapper<GroupDTO>> createElectiveGroup(
            @Valid @RequestBody ElectiveGroupInsertDTO electiveGroupInsertDTO) {

        Result<GroupRelationshipsDTO> relationshipsResult = groupRelationshipService.validateAndGetRelationships(electiveGroupInsertDTO);
        if (!relationshipsResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.badRequest(relationshipsResult.getErrorMessage()));
        }

        Result<Void> classroomScheduleResult = scheduleService.validateClassroomSchedule(electiveGroupInsertDTO.getClassroom(), electiveGroupInsertDTO.getSchedule(), null);
        if (!classroomScheduleResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(classroomScheduleResult.getErrorMessage()));
        }

        Result<Void> teacherScheduleResult = scheduleService.validateTeacherSchedule(electiveGroupInsertDTO.getTeacherId(), electiveGroupInsertDTO.getSchedule(), null);
        if (!teacherScheduleResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseWrapper.conflict(teacherScheduleResult.getErrorMessage()));
        }

        GroupDTO group = groupCreationService.createGroup(electiveGroupInsertDTO, relationshipsResult.getData());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(group,"Group"));
    }
}
