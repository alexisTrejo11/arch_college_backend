package io.github.alexistrejo11.architecture.college.schedule.service.group.crud;

import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupValidationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupScheduleUpdateDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.schedule.mappper.GroupMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.repository.TeacherRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupUpdateService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final GroupMapper groupMapper;
    private final ScheduleService scheduleService;
    private final GroupValidationService groupValidationService;
    private final GroupValidationService validationService;
    private final String currentSemester = AcademicData.getCurrentSchoolPeriod();

    public GroupDTO updateGroupSchedule(GroupScheduleUpdateDTO groupUpdateDTO) {
        Group group = groupRepository.findById(groupUpdateDTO.getGroup_id())
                .orElseThrow(() -> new EntityNotFoundException("Group with Id " + groupUpdateDTO.getGroup_id() + " not found"));

        group.setClassroom(groupUpdateDTO.getClassroom());
        group.setSchedule(scheduleService.mapScheduleDTOToEntity(groupUpdateDTO.getSchedule()));

        groupRepository.saveAndFlush(group);

        log.info("Group schedule updated for group with ID {}: Classroom set to {}, schedule updated", group.getId(), group.getClassroom());

        return groupMapper.entityToDTO(group);
    }

    public Result<Void> cancelGroup(String groupKey) {
        Result<Void> result = validationService.cancelGroupIfEligible(groupKey, currentSemester);

        if (result.isSuccess()) {
            log.info("Group with key {} canceled", groupKey);
        } else {
            log.info("Failed to cancel group with key {}: {}", groupKey, result.getErrorMessage());
        }

        return result;
    }

    @Transactional
    public GroupDTO removeTeacherToGroup(String groupKey, Long teacherId) {
        Group group = groupRepository.findByGroupKeyAndSchoolPeriod(groupKey, currentSemester)
                .orElseThrow(() -> new EntityNotFoundException("Group with Key " + groupKey + " not found"));

        Optional<Teacher> optionalTeacher = group.getTeachers()
                .stream()
                .filter(teacher -> teacher.getTeacherId().equals(teacherId))
                .findAny();

        if (optionalTeacher.isEmpty()) {
            throw new EntityNotFoundException("Teacher with ID " + teacherId + " not found in requested group");
        }

        group.removeTeacher(optionalTeacher.get());
        groupRepository.saveAndFlush(group);

        log.info("Teacher with ID {} removed from group with key {}", teacherId, groupKey);
        log.info("Group with key {} updated after teacher removal", groupKey);

        return groupMapper.entityToDTO(group);
    }

    @Transactional
    public Result<GroupDTO> addTeacherToGroup(String groupKey, Long teacherId) {
        Group group = groupRepository.findByGroupKeyAndSchoolPeriod(groupKey, currentSemester)
                .orElseThrow(() -> new EntityNotFoundException("Group with Key " + groupKey + " not found"));

        Teacher newTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with ID " + teacherId + " not found"));

        Result<Void> teacherResult = groupValidationService.validateNotDuplicatedTeacherInGroup(group, newTeacher);
        if (!teacherResult.isSuccess()) {
            return Result.error(teacherResult.getErrorMessage());
        }

        group.addTeacher(newTeacher);
        groupRepository.saveAndFlush(group);

        log.info("Teacher with ID {} added to group with key {}", teacherId, groupKey);
        log.info("Group with key {} updated after adding new teacher", groupKey);

        GroupDTO groupDTO = groupMapper.entityToDTO(group);
        return Result.success(groupDTO);
    }
}
