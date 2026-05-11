package io.github.alexistrejo11.architecture.college.grade.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.grade.model.grade.GradeValues;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherQualificationDTO {
    /*
    Key: StudentAccountNumber
    Value: Grade Value
    */
    @NotEmpty(message = "student_grades can't be null or empty")
    private Map<String, GradeValues> studentGradeMap = new HashMap<>();


    @JsonProperty("group_id")
    @NotNull(message = "group_id can't be null")
    @Positive(message = "group_id can't be negative")
    private Long groupId;
}
