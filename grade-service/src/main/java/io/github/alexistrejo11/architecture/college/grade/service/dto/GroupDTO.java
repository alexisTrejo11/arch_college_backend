package io.github.alexistrejo11.architecture.college.grade.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Grade.GradeDTO;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("head_teacher_account_number")
    private String headTeacherAccountNumber;

    @JsonProperty("group_type")
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    private String subjectName;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GradeDTO> grades;


}
