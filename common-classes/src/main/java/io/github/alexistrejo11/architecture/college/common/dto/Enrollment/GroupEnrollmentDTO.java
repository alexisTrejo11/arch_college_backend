package io.github.alexistrejo11.architecture.college.common.dto.Enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupType;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupEnrollmentDTO {
    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("group_type")
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @JsonProperty("subject_id")
    private Long subjectId;

    @JsonProperty("subject_name")
    private String subjectName;

    @JsonProperty("head_teacher_account_number")
    private String headTeacherAccountNumber;

    @JsonProperty("subject_type")
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    @JsonProperty("subject_credits")
    private int subjectCredits;

    @JsonProperty("school_period")
    private String schoolPeriod;

    @JsonProperty("enrollments")
    List<EnrollmentDTO> enrollments;

}
