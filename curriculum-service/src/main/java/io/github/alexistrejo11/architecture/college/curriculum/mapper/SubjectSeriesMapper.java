package io.github.alexistrejo11.architecture.college.curriculum.mapper;

import io.github.alexistrejo11.architecture.college.curriculum.service.dto.SubjectSeriesInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectSeriesDTO;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.SubjectSeries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObligatorySubjectMapper.class, ElectiveSubjectMapper.class})
public interface SubjectSeriesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obligatorySubjects", ignore = true)
    @Mapping(target = "electiveSubjects", ignore = true)
    SubjectSeries insertDtoToEntity(SubjectSeriesInsertDTO insetDTO);

    @Mapping(target = "obligatorySubjects", source = "obligatorySubjects")
    @Mapping(target = "electiveSubjects", source = "electiveSubjects")
    SubjectSeriesDTO entityToDTO(SubjectSeries subjectSeries);

}
