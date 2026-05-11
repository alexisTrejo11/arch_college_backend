package io.github.alexistrejo11.architecture.college.grade.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectId", ignore = true)
    @Mapping(target = "subjectType", ignore = true)
    @Mapping(target = "subjectName", ignore = true)
    @Mapping(target = "subjectCredits", ignore = true)
    @Mapping(target = "grades", ignore = true)
    Subject enrollmentDTOtoEntity(EnrollmentDTO enrollmentDTO);
}
