package io.github.alexistrejo11.architecture.college.grade.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Grade.GradeDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GradeInsertDTO;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;
import io.github.alexistrejo11.architecture.college.grade.model.grade.GradeTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gradeType", ignore = true)
    @Mapping(target = "gradeResult", ignore = true)
    @Mapping(target = "schoolPeriod", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorizedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Grade insertDtoToEntity(GradeInsertDTO gradeInsertDTO);

    GradeDTO entityToDTO(Grade grade);

    @Mapping(target = "subjectName", source = "subject.subjectName")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "subjectCredits", source = "subject.subjectCredits")
    @Mapping(target = "subjectType", source = "subject.subjectType")
    @Mapping(target = "groupType", source = "group.groupType")
    @Mapping(target = "lastSchoolPeriodCoursed", source = "schoolPeriod")
    @Mapping(target = "groupKey", ignore = true)
    @Mapping(target = "ordinaryCount", ignore = true)
    @Mapping(target = "extraordinaryCount", ignore = true)
    @Mapping(target = "authorized", ignore = true)
    GradeTrack entityToNamedDTO(Grade grade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gradeValue", ignore = true)
    @Mapping(target = "gradeType", ignore = true)
    @Mapping(target = "gradeResult", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "gradeStatus", ignore = true)
    @Mapping(target = "authorizedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Grade enrollmentDTOtoEntity(EnrollmentDTO enrollmentDTO);
}
