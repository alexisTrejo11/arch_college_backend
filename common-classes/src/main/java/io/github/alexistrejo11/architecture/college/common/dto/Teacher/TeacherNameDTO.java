package io.github.alexistrejo11.architecture.college.common.dto.Teacher;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherNameDTO {
    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "teacher_name")
    private String teacherName;
}