package io.github.alexistrejo11.architecture.college.student.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentInsertDTO;
import io.github.alexistrejo11.architecture.college.student.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "semestersCompleted", ignore = true)
    @Mapping(target = "incomeGeneration", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "professionalLineId", ignore = true)
    @Mapping(target = "professionalLineModality", ignore = true)
    Student insertDtoToEntity(StudentInsertDTO studentInsertDTO);

    StudentDTO entityToDTO(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "semestersCompleted", ignore = true)
    @Mapping(target = "incomeGeneration", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "professionalLineId", ignore = true)
    @Mapping(target = "professionalLineModality", ignore = true)
    void updatePersonalData(@MappingTarget Student student, StudentInsertDTO studentInsertDTO);
}
