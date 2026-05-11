package io.github.alexistrejo11.architecture.college.schedule.mappper;

import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherNameDTO;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "teacherName", ignore = true)
    @Mapping(target = "teacherTitle", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    Teacher dtoToEntity(TeacherDTO teacherDTO);
    List<Teacher> teacherDTOsToEntities(List<TeacherDTO> teacherDTOs);
    List<TeacherNameDTO> teachersToDTOs(List<Teacher> teachers);

}
