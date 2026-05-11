package io.github.alexistrejo11.architecture.college.account.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.ProfileDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.UserDTO;
import io.github.alexistrejo11.architecture.college.account.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO entityToDTO(User user);

    @Mapping(target = "mainRole", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    ProfileDTO entityToProfileDTO(User user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "mainRole", ignore = true)
    void studentDTOToProfileDTO(@MappingTarget ProfileDTO profileDTO, StudentDTO studentDTO);

}
