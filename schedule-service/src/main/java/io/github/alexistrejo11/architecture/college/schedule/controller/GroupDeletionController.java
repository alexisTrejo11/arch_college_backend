package io.github.alexistrejo11.architecture.college.schedule.controller;

import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupDeleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Groups API", description = "Endpoints for managing groups")
@RestController
@RequestMapping("/v1/api/groups")
@RequiredArgsConstructor
public class GroupDeletionController {

    private final GroupDeleteService groupDeleteService;

    @Operation(
            summary = "Remove Teacher from Group",
            description = "Removes a teacher from a specific group and updates the group's available spots accordingly",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Teacher successfully removed from group",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group or teacher not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @PatchMapping("/{key}/remove-teacher/{teacherId}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> deleteTeacherInGroup(
            @Parameter(description = "Unique key identifier of the group", required = true, example = "GRP-COMP101-2023-A")
            @Valid @PathVariable String key,

            @Parameter(description = "ID of the teacher to remove from the group", required = true, example = "12345")
            @PathVariable Long teacherId) {

        GroupDTO groupDTO = groupDeleteService.deleteTeacher(key, teacherId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseWrapper.updated(groupDTO, "Teacher removed and spots updated successfully"));
    }

    @Operation(
            summary = "Delete Group by Key",
            description = "Permanently deletes a group using its unique key identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group successfully deleted",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid group key format",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @DeleteMapping("/{key}")
    public ResponseEntity<ResponseWrapper<Void>> deleteGroupByKey(
            @Parameter(description = "Unique key identifier of the group to delete", required = true, example = "1102")
            @PathVariable String key) {

        groupDeleteService.deleteCurrentGroupByKey(key);
        return ResponseEntity.ok(ResponseWrapper.deleted("Group with key " + key));
    }
}