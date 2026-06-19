package io.github.alexistrejo11.architecture.college.grade.controller.grade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GroupDTO;
import io.github.alexistrejo11.architecture.college.grade.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Grade Groups API", description = "Endpoints for managing grade-related group operations")
@RestController
@RequestMapping("/v1/api/grades/groups")
@RequiredArgsConstructor
public class GradeGroupController {

    private final GroupService groupService;

    @Operation(
            summary = "Get Grade Group by ID",
            description = "Retrieves detailed information about a specific grade group using its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Grade group found successfully",
                            content = @Content(schema = @Schema(implementation = GroupDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Grade group not found",
                            content = @Content(schema = @Schema(implementation = ResponseWrapper.class)))
            }
    )
    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseWrapper<GroupDTO>> getGradeGroupById(
            @Parameter(description = "Unique numeric ID of the grade group", required = true, example = "12345")
            @PathVariable Long groupId) {

        Result<GroupDTO> groupResult = groupService.getGroupById(groupId);

        if (!groupResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseWrapper.notFound(groupResult.getErrorMessage()));
        }

        return ResponseEntity.ok(ResponseWrapper.ok(groupResult.getData(),
                "Grade group with ID " + groupId + " successfully retrieved"));
    }

    @Operation(
            summary = "Get Pending Grade Groups",
            description = "Retrieves a paginated list of grade groups that require processing or validation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pending grade groups retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    @GetMapping("/pending")
    public ResponseEntity<ResponseWrapper<Page<GroupDTO>>> getPendingGradeGroups(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GroupDTO> groups = groupService.getPendingGroups(pageable);

        return ResponseEntity.ok(ResponseWrapper.ok(groups,
                groups.getNumberOfElements() + " pending grade groups found"));
    }
}