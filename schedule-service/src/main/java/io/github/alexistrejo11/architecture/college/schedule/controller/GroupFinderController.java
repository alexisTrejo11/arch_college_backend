package io.github.alexistrejo11.architecture.college.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupReadService;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupFilterRequestDTO;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Groups API", description = "Endpoints for finding and retrieving groups using various search criteria")
@RestController
@RequestMapping("/v1/api/finder/groups")
@RequiredArgsConstructor
public class GroupFinderController {

    private final GroupReadService groupReadService;

    @Operation(
            summary = "Get Group by ID",
            description = "Retrieves detailed information about a specific group using its unique numeric identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group found successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found with specified ID",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> getGroupById(
            @Parameter(description = "Numeric ID of the group to retrieve", required = true, example = "12345")
            @PathVariable Long groupId) {

        Optional<GroupDTO> groupResult = groupReadService.getGroupById(groupId);
        return groupResult.map(groupDTO -> ResponseEntity.ok(ResponseWrapper.found(groupDTO, "Group")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseWrapper.notFound("Group with ID " + groupId + " not found")));
    }

    @Operation(
            summary = "Get Group by Key",
            description = "Retrieves a group using its unique alphanumeric key identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group found successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found with specified key",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/key/{key}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> getCurrentGroupByKey(
            @Parameter(description = "Unique key identifier of the group", required = true, example = "GRP-MATH101-2023-A")
            @PathVariable String key) {

        Optional<GroupDTO> groupResult = groupReadService.getCurrentGroupByKey(key);
        return groupResult.map(groupDTO -> ResponseEntity.ok(ResponseWrapper.found(groupDTO, "Group")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseWrapper.notFound("Group with key " + key + " not found")));
    }

    @Operation(
            summary = "Get Multiple Groups by IDs",
            description = "Retrieves multiple groups using a list of numeric identifiers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Groups found successfully",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    @GetMapping("/by-ids")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getGroupsByIds(
            @Parameter(description = "List of group IDs to retrieve", required = true, example = "[101, 202, 303]")
            @RequestParam List<Long> idList) {

        List<GroupDTO> groups = groupReadService.getGroupsByIds(idList);
        return ResponseEntity.ok(ResponseWrapper.found(groups, groups.size() + " groups found"));
    }

    @Operation(
            summary = "Search Groups with Filters",
            description = "Advanced search for groups using multiple filter criteria with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Groups matching criteria found",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    @GetMapping("/by")
    public ResponseEntity<ResponseWrapper<Page<GroupDTO>>> getGroups(
            @Valid @ModelAttribute GroupFilterRequestDTO filterRequest,
            @Parameter(hidden = true) Sort sort) {

        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize(), sort);
        GroupFinderFilter filter = new GroupFinderFilter()
                .withSchoolPeriod(filterRequest.getSchoolPeriod())
                .withSubjectType(filterRequest.getSubjectType())
                .withSubjectKey(filterRequest.getSubjectKey())
                .withClassroom(filterRequest.getClassroom());

        Page<GroupDTO> groups = groupReadService.findGroupsWithFilters(filter, pageable);
        return ResponseEntity.ok(ResponseWrapper.ok(groups, groups.getNumberOfElements() + " groups found"));
    }

    @Operation(
            summary = "Get Active Groups",
            description = "Retrieves paginated list of currently active groups",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Active groups retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    @GetMapping("/current")
    public ResponseEntity<ResponseWrapper<Page<GroupDTO>>> getCurrentGroups(
            @Parameter(description = "Page number for pagination (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GroupDTO> groups = groupReadService.getCurrentGroups(pageable);
        return ResponseEntity.ok(ResponseWrapper.found(groups, "Current active groups"));
    }

    @Operation(
            summary = "Get Teacher's Active Groups",
            description = "Retrieves all active groups associated with a specific teacher",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Teacher's groups found successfully",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    @GetMapping("/current/by-teacher/{teacherId}")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getCurrentGroupsByTeacherId(
            @Parameter(description = "Numeric ID of the teacher", required = true, example = "54321")
            @PathVariable Long teacherId) {

        List<GroupDTO> groups = groupReadService.getCurrentGroupByTeacherId(teacherId);
        return ResponseEntity.ok(ResponseWrapper.found(groups, groups.size() + " groups found for teacher"));
    }

    @Operation(
            summary = "Get Groups by Building",
            description = "Retrieves active groups located in a specific building",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Building groups found successfully",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    @GetMapping("/current/by-building/{buildingLetter}")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getCurrentGroupsByBuilding(
            @Parameter(description = "Building identifier (single character)", example = "B")
            @PathVariable String buildingLetter) {

        List<GroupDTO> groups = groupReadService.getCurrentGroupsByClassroomPrefix(buildingLetter);
        return ResponseEntity.ok(ResponseWrapper.found(groups, groups.size() + " groups in building " + buildingLetter));
    }
}