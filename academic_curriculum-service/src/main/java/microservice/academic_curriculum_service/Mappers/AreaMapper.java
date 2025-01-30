package microservice.academic_curriculum_service.Mappers;

import microservice.common_classes.DTOs.Area.AreaDTO;
import microservice.common_classes.DTOs.Area.AreaInsertDTO;
import microservice.common_classes.DTOs.Area.AreaWithRelationsDTO;
import microservice.academic_curriculum_service.Model.Career.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = {ElectiveSubjectMapper.class, ObligatorySubjectMapper.class})
public interface AreaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Area insertDtoToEntity(AreaInsertDTO studentInsertDTO);

    AreaDTO entityToDTO(Area area);

    @Mapping(target = "ordinarySubjects", source = "ordinarySubjects")
    @Mapping(target = "electiveSubjects", source = "electiveSubjects")
    AreaWithRelationsDTO entityToDTOWithRelations(Area area);

}
