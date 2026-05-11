package io.github.alexistrejo11.architecture.college.common.dto.Subject;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSeriesDTO {
    private Long id;

    private String name;

    private List<ObligatorySubjectDTO> obligatorySubjects = new ArrayList<>();
    private List<ElectiveSubjectDTO> electiveSubjects = new ArrayList<>();
}
