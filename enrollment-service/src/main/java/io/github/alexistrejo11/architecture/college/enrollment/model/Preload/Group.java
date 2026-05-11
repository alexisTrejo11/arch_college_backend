package io.github.alexistrejo11.architecture.college.enrollment.model.Preload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ScheduleDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherNameDTO;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupStatus;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupType;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@Document(collection = "groups")
public class Group {
    @Id
    private Long id;

    @JsonProperty("subject_id")
    private Long subjectId;

    @JsonProperty("subject_name")
    private String subjectName;

    @JsonProperty("subject_key")
    private String subjectKey;

    @JsonProperty("subject_type")
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    @JsonProperty("group_key")
    private String groupKey;

    @JsonProperty("available_spots")
    private int availableSpots;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @JsonProperty("group_type")
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @JsonProperty("school_period")
    private String schoolPeriod;

    @JsonProperty("schedule")
    private List<ScheduleDTO> schedule;

    @JsonProperty("classroom")
    private String classroom;

    @JsonProperty("teachers")
    private List<TeacherNameDTO> teacherDTOS;

    @JsonProperty("head_teacher_accountNumber")
    private String headTeacherAccountNumber;
}
