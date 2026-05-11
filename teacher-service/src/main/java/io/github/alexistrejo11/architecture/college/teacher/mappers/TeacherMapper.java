package io.github.alexistrejo11.architecture.college.teacher.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.teacher.service.dto.TeacherInsertDTO;
import io.github.alexistrejo11.architecture.college.teacher.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    Teacher insertDtoToEntity(TeacherInsertDTO studentInsertDTO);

    TeacherDTO entityToDTO(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updatePersonalData(@MappingTarget Teacher teacher, TeacherInsertDTO studentInsertDTO);
}
