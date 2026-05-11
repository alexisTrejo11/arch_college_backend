package io.github.alexistrejo11.architecture.college.curriculum.mapper;


import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineDTO;
import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.ProfessionalLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessionalLineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    ProfessionalLine insertDtoToEntity(ProfessionalLineInsertDTO professionalLineInsertDTO);

    ProfessionalLineDTO entityToDTO(ProfessionalLine professionalLine);
}
