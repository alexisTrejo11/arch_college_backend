package microservice.common_classes.DTOs.Group;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.common_classes.DTOs.Teacher.TeacherNameDTO;
import microservice.common_classes.Utils.Group.GroupStatus;
import microservice.common_classes.Utils.Group.GroupType;
import microservice.common_classes.Utils.SubjectType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupDTO {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the group", example = "12345")
    private Long id;

    @JsonProperty("group_key")
    @Schema(description = "Unique key representing the group", example = "G123")
    private String groupKey;

    @JsonProperty("subject_id")
    @Schema(description = "Identifier of the subject associated with the group", example = "987")
    private Long subjectId;

    @JsonProperty("subject_key")
    @Schema(description = "Key representing the subject", example = "MATH101")
    private String subjectKey;

    @JsonProperty("subject_name")
    @Schema(description = "Name of the subject", example = "Mathematics")
    private String subjectName;

    @JsonProperty("subject_type")
    @Schema(description = "Type of the subject (e.g., lecture, practical)", example = "LECTURE")
    private SubjectType subjectType;

    @JsonProperty("available_spots")
    @Schema(description = "Number of available spots in the group", example = "5")
    private int availableSpots;

    @JsonProperty("status")
    @Schema(description = "Current status of the group (e.g., active, inactive)", example = "ACTIVE")
    private GroupStatus groupStatus;

    @JsonProperty("head_teacher_accountNumber")
    @Schema(description = "Account number of the head teacher", example = "T12345")
    private String headTeacherAccountNumber;

    @JsonProperty("group_type")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of the group (e.g., regular, special)", example = "ORDINAL")
    private GroupType groupType = GroupType.ORDINAL;

    @JsonProperty("schedule")
    @Schema(description = "List of schedules for the group", example = "[{\"day\": \"Monday\", \"startTime\": \"10:00\", \"endTime\": \"12:00\"}]")
    private List<ScheduleDTO> schedule;

    @JsonProperty("classroom")
    @Schema(description = "Classroom where the group meets", example = "A-101")
    private String classroom;

    @Schema(description = "List of teachers assigned to the group")
    private List<TeacherNameDTO> teachers = new ArrayList<>();
}
