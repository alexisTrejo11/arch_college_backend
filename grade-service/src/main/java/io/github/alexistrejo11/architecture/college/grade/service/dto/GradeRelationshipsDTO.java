package io.github.alexistrejo11.architecture.college.grade.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeRelationshipsDTO {
    private StudentDTO studentDTO;
    private SubjectDTO subjectDTO;
    private GroupDTO groupDTO;
    private ProfessionalLineDTO professionalLineDTOS;
}
