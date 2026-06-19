package io.github.alexistrejo11.architecture.college.common.dto.Group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupType;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;


@Data
@NoArgsConstructor
public class GroupNamedDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("subject_name")
    private String subjectName;

    @JsonProperty("teacher_name")
    private String teacherName;

    @JsonProperty("subject_type")
    private SubjectType subjectType;

    @JsonProperty("group_type")
    private GroupType groupType;
}
