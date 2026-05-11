package io.github.alexistrejo11.architecture.college.grade.mapper;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.EnrollmentDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GroupDTO;
import io.github.alexistrejo11.architecture.college.grade.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GradeMapper.class)
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "headTeacherAccountNumber", ignore = true)
    @Mapping(target = "isGroupQualified", ignore = true)
    @Mapping(target = "qualifiedAt", ignore = true)
    @Mapping(target = "groupType", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Group enrollmentDTOtoEntity(EnrollmentDTO enrollmentDTO);

    @Mapping(target = "grades", source = "grades")
    @Mapping(target = "subjectName", source = "group.subject.subjectName")
    GroupDTO entityToDTO(Group group);

}
