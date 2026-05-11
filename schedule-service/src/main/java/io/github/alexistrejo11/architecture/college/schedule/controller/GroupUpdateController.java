package io.github.alexistrejo11.architecture.college.schedule.controller;

import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupScheduleUpdateDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.implementation.GroupReadServiceImpl;
import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupSpotsService;
import io.github.alexistrejo11.architecture.college.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Groups API", description = "Endpoints for managing and updating group information")
@RestController
@RequestMapping("/v1/api/groups")
@RequiredArgsConstructor
public class GroupUpdateController {

    private final ScheduleService scheduleService;
    private final GroupUpdateService groupUpdateService;
    private final GroupSpotsService groupSpotsService;
    private final GroupReadServiceImpl groupFinderServiceImpl;

    @Operation(
            summary = "Update Group Schedule",
            description = "Updates the schedule information for an existing group with conflict validation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Schedule updated successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Schedule conflict detected",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/update-schedule")
    public ResponseEntity<ResponseWrapper<GroupDTO>> updateGroupSchedule(
            @Parameter(description = "Group schedule update data", required = true,
                    content = @Content(schema = @Schema(implementation = GroupScheduleUpdateDTO.class)))
            @Valid @RequestBody GroupScheduleUpdateDTO groupScheduleUpdateDTO) {

        Result<Void> validationResult = scheduleService.validateClassroomSchedule(
                groupScheduleUpdateDTO.getClassroom(),
                groupScheduleUpdateDTO.getSchedule(),
                groupScheduleUpdateDTO.getGroup_id()
        );

        if (!validationResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseWrapper.conflict(validationResult.getErrorMessage()));
        }

        GroupDTO updatedGroup = groupUpdateService.updateGroupSchedule(groupScheduleUpdateDTO);
        return ResponseEntity.ok(ResponseWrapper.updated(updatedGroup, "Group schedule updated"));
    }

    @Operation(
            summary = "Assign Teacher to Group",
            description = "Assigns a teacher to a group with schedule conflict validation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Teacher assigned successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Teacher schedule conflict",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/{key}/add-teacher/{teacherId}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> addTeacherToGroup(
            @Parameter(description = "Unique group key identifier", required = true, example = "GRP-MATH101-2023-A")
            @Valid @PathVariable String key,

            @Parameter(description = "Numeric ID of the teacher", required = true, example = "12345")
            @PathVariable Long teacherId) {

        Optional<GroupDTO> group = groupFinderServiceImpl.getCurrentGroupByKey(key);
        if (group.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseWrapper.notFound("Group with key " + key));
        }

        Result<Void> scheduleValidation = scheduleService.validateTeacherSchedule(
                teacherId,
                group.get().getSchedule(),
                group.get().getId()
        );

        if (!scheduleValidation.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseWrapper.conflict(scheduleValidation.getErrorMessage()));
        }

        Result<GroupDTO> updateResult = groupUpdateService.addTeacherToGroup(key, teacherId);
        return updateResult.isSuccess()
                ? ResponseEntity.ok(ResponseWrapper.ok(updateResult.getData(), "Teacher assigned successfully"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.badRequest(updateResult.getErrorMessage()));
    }

    @Operation(
            summary = "Increase Group Capacity",
            description = "Increases the available spots in a group by specified amount",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Spots increased successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid spot value",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/{groupId}/add_spots/{spotsToAdd}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> increaseGroupSpotsByKey(
            @Parameter(description = "Numeric group ID", required = true, example = "101")
            @Valid @PathVariable Long groupId,

            @Parameter(description = "Number of spots to add (positive integer)", example = "5")
            @PathVariable int spotsToAdd) {

        Optional<GroupDTO> group = groupFinderServiceImpl.getGroupById(groupId);
        if (group.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseWrapper.notFound("Group", "Id", groupId));
        }

        Result<Void> validation = groupSpotsService.validateSpotIncrease(spotsToAdd);
        if (!validation.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseWrapper.badRequest(validation.getErrorMessage()));
        }

        GroupDTO updatedGroup = groupSpotsService.addSpots(groupId, spotsToAdd);
        return ResponseEntity.ok(ResponseWrapper.ok(updatedGroup,
                spotsToAdd + " spots added successfully. Total available: " + updatedGroup.getAvailableSpots()));
    }

    @Operation(
            summary = "Decrease Group Capacity",
            description = "Decreases the available spots in a group by one",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Spot decreased successfully",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid operation",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/{groupId}/decrease-spot")
    public ResponseEntity<ResponseWrapper<Void>> decreaseGroupSpot(
            @Parameter(description = "Numeric group ID", required = true, example = "101")
            @Valid @PathVariable Long groupId) {

        Result<Void> result = groupSpotsService.decreaseSpot(groupId);
        return result.isSuccess()
                ? ResponseEntity.ok(ResponseWrapper.ok("Spot decreased. Current availability updated"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.badRequest(result.getErrorMessage()));
    }

    @Operation(
            summary = "Increase Group Capacity by One",
            description = "Increases the available spots in a group by one",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Spot increased successfully",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PutMapping("/{groupId}/increase-spot")
    public ResponseEntity<ResponseWrapper<Void>> increaseGroupSpot(
            @Parameter(description = "Numeric group ID", required = true, example = "101")
            @Valid @PathVariable Long groupId) {

        groupSpotsService.increaseSpot(groupId);
        return ResponseEntity.ok(ResponseWrapper.ok("Spot increased. Current availability updated"));
    }
}