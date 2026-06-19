package io.github.alexistrejo11.architecture.college.enrollment.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Grade.GradeDTO;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Grade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    Grade dtoToEntity(GradeDTO gradeDTO);
}
