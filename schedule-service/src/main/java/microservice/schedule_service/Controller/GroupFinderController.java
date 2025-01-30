package microservice.schedule_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Group.GroupDTO;
import microservice.common_classes.Utils.Response.ResponseWrapper;
import microservice.schedule_service.Service.GroupServices.GroupFinderService;
import microservice.schedule_service.Utils.GroupFilterRequestDTO;
import microservice.schedule_service.Utils.GroupFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Groups API", description = "Endpoints for finding groups by various parameters and filters")
@RestController
@RequestMapping("/v1/api/finder/groups")
@RequiredArgsConstructor
public class GroupFinderController {

    private final GroupFinderService groupFinderService;

    @Operation(summary = "Get Group by ID", description = "Retrieve a group by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Group found", content = @Content(schema = @Schema(implementation = GroupDTO.class)))
    @ApiResponse(responseCode = "404", description = "Group not found")
    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> getGroupById(@PathVariable Long groupId) {
        Optional<GroupDTO> groupResult = groupFinderService.getGroupById(groupId);
        return groupResult.map(groupDTO -> ResponseEntity.ok(ResponseWrapper.found(groupDTO, "Group")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseWrapper.notFound("Group with Id " + groupId + " not found")));
    }

    @Operation(summary = "Get Group by Key", description = "Retrieve a group by its unique key")
    @GetMapping("/key/{key}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> getCurrentGroupByKey(@PathVariable String key) {
        Optional<GroupDTO> groupResult = groupFinderService.getCurrentGroupByKey(key);
        return groupResult.map(groupDTO -> ResponseEntity.ok(ResponseWrapper.found(groupDTO, "Group")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseWrapper.notFound("Group with Key " + key + " not found")));
    }

    @Operation(summary = "Get Groups by IDs")
    @GetMapping("/by-ids")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getGroupsByIds(@RequestParam List<Long> idList) {
        List<GroupDTO> groups = groupFinderService.getGroupsByIds(idList);
        return ResponseEntity.ok(ResponseWrapper.found(groups, "Groups"));
    }

    @Operation(summary = "Find Groups with Dynamic Filters")
    @GetMapping("/by")
    public ResponseEntity<ResponseWrapper<Page<GroupDTO>>> getGroups(@Valid @ModelAttribute GroupFilterRequestDTO filterRequest) {
        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize(), filterRequest.getSort());

        GroupFinderFilter groupFinderFilter = new GroupFinderFilter()
                .withSchoolPeriod(filterRequest.getSchoolPeriod())
                .withSubjectType(filterRequest.getSubjectType())
                .withSubjectKey(filterRequest.getSubjectKey())
                .withClassroom(filterRequest.getClassroom());

        Page<GroupDTO> groups = groupFinderService.findGroupsWithFilters(groupFinderFilter, pageable);
        return ResponseEntity.ok(ResponseWrapper.ok(groups, "Groups successfully fetched"));
    }

    @Operation(summary = "Get Current Groups")
    @GetMapping("/current")
    public ResponseEntity<ResponseWrapper<Page<GroupDTO>>> getCurrentGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GroupDTO> groups = groupFinderService.getCurrentGroups(pageable);
        return ResponseEntity.ok(ResponseWrapper.found(groups, "Current Groups"));
    }

    @Operation(summary = "Get Current Groups by Teacher ID")
    @GetMapping("/current/by-teacher/{teacherId}")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getCurrentGroupsByTeacherId(@PathVariable Long teacherId) {
        List<GroupDTO> groups = groupFinderService.getCurrentGroupByTeacherId(teacherId);
        return ResponseEntity.ok(ResponseWrapper.found(groups, "Groups by Teacher ID"));
    }

    @Operation(summary = "Get Current Groups by Building")
    @GetMapping("/current/by-building/{buildingLetter}")
    public ResponseEntity<ResponseWrapper<List<GroupDTO>>> getCurrentGroupsByBuilding(@PathVariable char buildingLetter) {
        List<GroupDTO> groups = groupFinderService.getCurrentGroupsByClassroomPrefix(String.valueOf(buildingLetter));
        return ResponseEntity.ok(ResponseWrapper.found(groups, "Groups by Building"));
    }
}