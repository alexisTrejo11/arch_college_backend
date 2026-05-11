package io.github.alexistrejo11.architecture.college.curriculum.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaWithRelationsDTO;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = {ElectiveSubjectMapper.class, ObligatorySubjectMapper.class})
public interface AreaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "obligatorySubjects", ignore = true)
    @Mapping(target = "electiveSubjects", ignore = true)
    Area insertDtoToEntity(AreaInsertDTO studentInsertDTO);

    AreaDTO entityToDTO(Area area);

    @Mapping(target = "ordinarySubjects", ignore = true)
    @Mapping(target = "electiveSubjects", ignore = true)
    AreaWithRelationsDTO entityToDTOWithRelations(Area area);

}
