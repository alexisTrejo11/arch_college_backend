package io.github.alexistrejo11.architecture.college.curriculum.mapper;

import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ElectiveSubject;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectInsertDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ElectiveSubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "career", ignore = true)
    @Mapping(target = "series", ignore = true)
    @Mapping(target = "credits", ignore = true)
    @Mapping(target = "professionalLine", ignore = true)
    ElectiveSubject insertDtoToEntity(ElectiveSubjectInsertDTO electiveSubjectInsertDTO);

    @Mapping(target = "id", source = "electiveSubjectId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "career", ignore = true)
    @Mapping(target = "series", ignore = true)
    @Mapping(target = "credits", ignore = true)
    @Mapping(target = "professionalLine", ignore = true)
    ElectiveSubject updateDtoToEntity(ElectiveSubjectInsertDTO electiveSubjectInsertDTO, Long electiveSubjectId);

    @Mapping(target = "areaId", source = "area.id")
    @Mapping(target = "seriesId", source = "series.id")
    @Mapping(target = "professionalLineId", source = "professionalLine.id")
    ElectiveSubjectDTO entityToDTO(ElectiveSubject electiveSubject);
}
