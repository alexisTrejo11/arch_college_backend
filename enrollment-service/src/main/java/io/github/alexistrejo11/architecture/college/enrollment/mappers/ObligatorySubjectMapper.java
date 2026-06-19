package io.github.alexistrejo11.architecture.college.enrollment.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ObligatorySubject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObligatorySubjectMapper {

    ObligatorySubject dtoToEntity(ObligatorySubjectDTO obligatorySubjectDTO);
}
