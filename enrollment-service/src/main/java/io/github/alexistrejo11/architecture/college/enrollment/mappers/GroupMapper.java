package io.github.alexistrejo11.architecture.college.enrollment.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "headTeacherAccountNumber", source = "groupDTO.headTeacherAccountNumber")
    Group dtoToEntity(GroupDTO groupDTO);

}
