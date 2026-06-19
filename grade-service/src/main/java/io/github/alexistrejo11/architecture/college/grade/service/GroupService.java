package io.github.alexistrejo11.architecture.college.grade.service;

import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.GroupEnrollmentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GroupDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.TeacherQualificationDTO;
import io.github.alexistrejo11.architecture.college.grade.model.Group;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {
    Result<GroupDTO> getGroupById(Long groupId);
    Page<GroupDTO> getPendingGroups(Pageable pageable);
    List<GroupDTO> getTeacherGroupsPendingToBeQualified(String teacherAccountNumber);
    List<GroupDTO> getTeacherGroupsQualified(String teacherAccountNumber);

    Result<Void> validateGroupQualification(TeacherQualificationDTO teacherQualificationDTO, String teacherAccountNumber);
    Result<Void> validateGroupGradingPeriodTime();

    Group createGroupFromEnrollment(GroupEnrollmentDTO groupEnrollmentDTO, Subject subject);
    void addGroupQualifications(TeacherQualificationDTO teacherQualificationDTO, String teacherAccountNumber);
    void undoGroupQualifications(Long groupId, String teacherAccountNumber);

    void addGradesToAcademicHistoryAsync(Long groupId);
}
