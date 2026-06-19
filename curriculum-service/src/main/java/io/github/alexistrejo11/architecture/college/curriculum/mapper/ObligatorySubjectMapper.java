package io.github.alexistrejo11.architecture.college.curriculum.mapper;

import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ObligatorySubject;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectInsertDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ObligatorySubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "career", ignore = true)
    @Mapping(target = "series", ignore = true)
    ObligatorySubject insertDtoToEntity(ObligatorySubjectInsertDTO obligatorySubjectInsertDTO);

    @Mapping(target = "id", source = "ordinarySubjectId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "career", ignore = true)
    @Mapping(target = "series", ignore = true)
    ObligatorySubject updateDtoToEntity(ObligatorySubjectInsertDTO obligatorySubjectInsertDTO, Long ordinarySubjectId);

    @Mapping(target = "areaId", source = "area.id")
    @Mapping(target = "seriesId", source = "series.id")
    ObligatorySubjectDTO entityToDTO(ObligatorySubject obligatorySubject);
}
