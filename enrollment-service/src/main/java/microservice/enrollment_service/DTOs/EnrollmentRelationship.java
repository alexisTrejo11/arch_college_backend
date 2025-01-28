package microservice.enrollment_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.enrollment_service.Model.Preload.*;

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
