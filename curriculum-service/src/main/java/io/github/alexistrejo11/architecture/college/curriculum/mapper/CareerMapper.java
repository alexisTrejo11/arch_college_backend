package io.github.alexistrejo11.architecture.college.curriculum.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Career;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CareerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "obligatorySubjects", ignore = true)
    @Mapping(target = "electiveSubjects", ignore = true)
    Career insertDtoToEntity(CareerInsertDTO careerInsertDTO);

    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "obligatorySubjects", ignore = true)
    @Mapping(target = "electiveSubjects", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Career career , CareerInsertDTO careerInsertDTO);

    CareerDTO entityToDTO(Career career);
}
