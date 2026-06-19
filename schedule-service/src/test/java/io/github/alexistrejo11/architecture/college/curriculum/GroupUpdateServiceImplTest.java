package io.github.alexistrejo11.architecture.college.curriculum;

import jakarta.persistence.EntityNotFoundException;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupScheduleUpdateDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.schedule.mappper.GroupMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.repository.TeacherRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupUpdateService;
import io.github.alexistrejo11.architecture.college.schedule.service.ScheduleService;
import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupUpdateServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private GroupValidationService groupValidationService;

    @InjectMocks
    private GroupUpdateService groupUpdateService;

    private final String groupKey = "1102-3";
    private final Long teacherId = 1L;

    @Test
    void updateGroupSchedule_ShouldUpdateScheduleAndReturnGroupDTO() {
        Group group = new Group();
        group.setId(1L);

        GroupScheduleUpdateDTO groupUpdateDTO = new GroupScheduleUpdateDTO();
        groupUpdateDTO.setGroup_id(1L);
        groupUpdateDTO.setClassroom("New Classroom");

        when(groupRepository.findById(groupUpdateDTO.getGroup_id())).thenReturn(Optional.of(group));
        when(scheduleService.mapScheduleDTOToEntity(groupUpdateDTO.getSchedule())).thenReturn(group.getSchedule());
        when(groupMapper.entityToDTO(group)).thenReturn(new GroupDTO());

        GroupDTO result = groupUpdateService.updateGroupSchedule(groupUpdateDTO);

        verify(groupRepository).saveAndFlush(group);
        assertNotNull(result);
    }

    @Test
    void updateGroupSchedule_GroupNotFound_ShouldThrowException() {
        GroupScheduleUpdateDTO groupUpdateDTO = new GroupScheduleUpdateDTO();
        groupUpdateDTO.setGroup_id(1L);

        when(groupRepository.findById(groupUpdateDTO.getGroup_id())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupUpdateService.updateGroupSchedule(groupUpdateDTO));
    }

    @Test
    void cancelGroup_ShouldCancelGroupIfEligible() {
        when(groupValidationService.cancelGroupIfEligible(eq(groupKey), anyString())).thenReturn(Result.success());

        Result<Void> result = groupUpdateService.cancelGroup(groupKey);

        assertTrue(result.isSuccess());
        verify(groupValidationService).cancelGroupIfEligible(eq(groupKey), anyString());
    }

    @Test
    void cancelGroup_GroupNotEligible_ShouldReturnError() {
        when(groupValidationService.cancelGroupIfEligible(eq(groupKey), anyString())).thenReturn(Result.error("Error"));

        Result<Void> result = groupUpdateService.cancelGroup(groupKey);

        assertFalse(result.isSuccess());
        assertEquals("Error", result.getErrorMessage());
    }

    @Test
    void removeTeacherToGroup_ShouldRemoveTeacherAndReturnGroupDTO() {
        Group group = new Group();
        group.setId(1L);
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);

        group.addTeacher(teacher);

        when(groupRepository.findByGroupKeyAndSchoolPeriod(eq(groupKey), anyString())).thenReturn(Optional.of(group));
        when(groupMapper.entityToDTO(group)).thenReturn(new GroupDTO());

        GroupDTO result = groupUpdateService.removeTeacherToGroup(groupKey, teacherId);

        verify(groupRepository).saveAndFlush(group);
        assertNotNull(result);
    }

    @Test
    void removeTeacherToGroup_TeacherNotFound_ShouldThrowException() {
        Group group = new Group();
        group.setId(1L);

        when(groupRepository.findByGroupKeyAndSchoolPeriod(eq(groupKey), anyString())).thenReturn(Optional.of(group));

        assertThrows(EntityNotFoundException.class, () -> groupUpdateService.removeTeacherToGroup(groupKey, teacherId));
    }

    @Test
    void addTeacherToGroup_ShouldAddTeacherAndReturnGroupDTO() {
        Group group = new Group();
        group.setId(1L);
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);

        when(groupRepository.findByGroupKeyAndSchoolPeriod(eq(groupKey), anyString())).thenReturn(Optional.of(group));
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(groupValidationService.validateNotDuplicatedTeacherInGroup(group, teacher)).thenReturn(Result.success());
        when(groupMapper.entityToDTO(group)).thenReturn(new GroupDTO());

        Result<GroupDTO> result = groupUpdateService.addTeacherToGroup(groupKey, teacherId);

        verify(groupRepository).saveAndFlush(group);
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
    }

    @Test
    void addTeacherToGroup_TeacherAlreadyInGroup_ShouldReturnError() {
        Group group = new Group();
        group.setId(1L);
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);

        when(groupRepository.findByGroupKeyAndSchoolPeriod(eq(groupKey), anyString())).thenReturn(Optional.of(group));
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(groupValidationService.validateNotDuplicatedTeacherInGroup(group, teacher))
                .thenReturn(Result.error("Teacher is already on the group"));

        Result<GroupDTO> result = groupUpdateService.addTeacherToGroup(groupKey, teacherId);

        assertFalse(result.isSuccess());
        assertEquals("Teacher is already on the group", result.getErrorMessage());
    }
}

