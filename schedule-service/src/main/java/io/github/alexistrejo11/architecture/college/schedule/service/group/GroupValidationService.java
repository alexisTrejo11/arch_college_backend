package io.github.alexistrejo11.architecture.college.schedule.service.group;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupStatus;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupValidationService {
    private final GroupRepository groupRepository;

    public Result<Void> cancelGroupIfEligible(String groupKey, String currentSemester) {
        Group group = groupRepository.findByGroupKeyAndSchoolPeriod(groupKey, currentSemester)
                .orElseThrow(() -> new EntityNotFoundException("Group with Key " + groupKey + " not found"));

        if (group.getAvailableSpots() != group.getTotalSpots()) {
            return Result.error("Group can't be cancelled if it has enrollments");
        }

        group.setGroupStatus(GroupStatus.CANCELLED);
        groupRepository.save(group);
        return Result.success();
    }


    public Result<Void> validateNotDuplicatedTeacherInGroup(Group group, Teacher newTeacher) {
        Optional<Teacher> optionalTeacher = group.getTeachers().stream().filter(teacher -> teacher.equals(newTeacher)).findAny();
        if (optionalTeacher.isPresent()) {
            return  Result.error ("Teacher is already on the group");
        }

        return Result.success();
    }
}
