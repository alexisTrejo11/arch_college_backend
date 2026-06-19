package io.github.alexistrejo11.architecture.college.enrollment.mappers;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectSeriesDTO;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.SubjectSeries;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectSeriesMapper {

    SubjectSeries dtoToEntity(SubjectSeriesDTO subjectSeriesDTO);
}
