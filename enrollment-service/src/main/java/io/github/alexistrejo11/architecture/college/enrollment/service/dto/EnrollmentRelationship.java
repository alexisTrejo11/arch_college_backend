package io.github.alexistrejo11.architecture.college.enrollment.service.dto;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRelationship {
    private List<Grade> studentGrades;
    private Student student;
    private Group group;
    private ObligatorySubject obligatorySubject;
    private ElectiveSubject electiveSubject;
}
