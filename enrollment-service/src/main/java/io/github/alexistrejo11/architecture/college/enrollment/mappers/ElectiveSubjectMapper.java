package io.github.alexistrejo11.architecture.college.enrollment.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ElectiveSubject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ElectiveSubjectMapper {

    ElectiveSubject dtoToEntity(ElectiveSubjectDTO electiveSubjectDTO);
}
