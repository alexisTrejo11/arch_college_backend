package io.github.alexistrejo11.architecture.college.schedule.mappper;

import io.github.alexistrejo11.architecture.college.common.dto.Group.ElectiveGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ObligatoryGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectId", ignore = true)
    @Mapping(target = "subjectName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "headTeacherFromTeachers", ignore = true)
    @Mapping(target = "subjectType", ignore = true)
    @Mapping(target = "subjectKey", ignore = true)
    @Mapping(target = "groupKey", ignore = true)
    @Mapping(target = "availableSpots", ignore = true)
    @Mapping(target = "schoolPeriod", ignore = true)
    @Mapping(target = "headTeacherAccountNumber", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    Group insertDtoToEntity(ObligatoryGroupInsertDTO obligatoryGroupInsertDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectId", ignore = true)
    @Mapping(target = "subjectName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "headTeacherFromTeachers", ignore = true)
    @Mapping(target = "subjectType", ignore = true)
    @Mapping(target = "subjectKey", ignore = true)
    @Mapping(target = "groupKey", ignore = true)
    @Mapping(target = "availableSpots", ignore = true)
    @Mapping(target = "totalSpots", ignore = true)
    @Mapping(target = "schoolPeriod", ignore = true)
    @Mapping(target = "headTeacherAccountNumber", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    Group insertDtoToEntity(ElectiveGroupInsertDTO electiveGroupInsertDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "groupKey", ignore = true)
    @Mapping(target = "schoolPeriod", ignore = true)
    @Mapping(target = "headTeacherFromTeachers", ignore = true)
    @Mapping(target = "subjectType", ignore = true)
    @Mapping(target = "subjectKey", ignore = true)
    @Mapping(target = "subjectName", ignore = true)
    @Mapping(target = "availableSpots", ignore = true)
    @Mapping(target = "headTeacherAccountNumber", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    void updateDto(@MappingTarget Group group,  ObligatoryGroupInsertDTO OBligatoryGroupInsertDTO);

    @Mapping(target = "teachers", source = "teachers")
    @Mapping(target = "groupType", ignore = true)
    GroupDTO entityToDTO(Group group);


}
