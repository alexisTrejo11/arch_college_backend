package io.github.alexistrejo11.architecture.college.curriculum;

import jakarta.persistence.EntityNotFoundException;
import io.github.alexistrejo11.architecture.college.common.models.group.GroupStatus;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupValidationServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupValidationService groupValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelGroupIfEligible_GroupNotFound_ShouldThrowEntityNotFoundException() {
        // Arrange
        String groupKey = "groupKey1";
        String currentSemester = "2023-2";

        when(groupRepository.findByGroupKeyAndSchoolPeriod(groupKey, currentSemester))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                groupValidationService.cancelGroupIfEligible(groupKey, currentSemester));

        assertEquals("Group with Key " + groupKey + " not found", exception.getMessage());
    }

    @Test
    void cancelGroupIfEligible_GroupHasEnrollments_ShouldReturnErrorResult() {
        // Arrange
        Group group = new Group();
        group.setGroupKey("groupKey1");
        group.setSchoolPeriod("2023-2");
        group.setAvailableSpots(5);
        group.setTotalSpots(10);

        when(groupRepository.findByGroupKeyAndSchoolPeriod("groupKey1", "2023-2"))
                .thenReturn(Optional.of(group));

        // Act
        Result<Void> result = groupValidationService.cancelGroupIfEligible("groupKey1", "2023-2");

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Group can't be cancelled if it has enrollments", result.getErrorMessage());
        verify(groupRepository, never()).save(any(Group.class));
    }

    @Test
    void cancelGroupIfEligible_GroupEligible_ShouldCancelGroupAndReturnSuccess() {
        // Arrange
        Group group = new Group();
        group.setGroupKey("groupKey1");
        group.setSchoolPeriod("2023-2");
        group.setAvailableSpots(10);
        group.setTotalSpots(10);
        group.setGroupStatus(GroupStatus.ACTIVE);

        when(groupRepository.findByGroupKeyAndSchoolPeriod("groupKey1", "2023-2"))
                .thenReturn(Optional.of(group));

        // Act
        Result<Void> result = groupValidationService.cancelGroupIfEligible("groupKey1", "2023-2");

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(GroupStatus.CANCELLED, group.getGroupStatus());
        verify(groupRepository).save(group);
    }

    @Test
    void validateNotDuplicatedTeacherInGroup_TeacherAlreadyInGroup_ShouldReturnErrorResult() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setTeacherId(1L);

        Group group = new Group();
        group.setTeachers(new ArrayList<>(List.of(teacher)));

        // Act
        Result<Void> result = groupValidationService.validateNotDuplicatedTeacherInGroup(group, teacher);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Teacher is already on the group", result.getErrorMessage());
    }

    @Test
    void validateNotDuplicatedTeacherInGroup_TeacherNotInGroup_ShouldReturnSuccess() {
        // Arrange
        Teacher existingTeacher = new Teacher();
        existingTeacher.setTeacherId(1L);

        Teacher newTeacher = new Teacher();
        newTeacher.setTeacherId(2L);

        Group group = new Group();
        group.setTeachers(new ArrayList<>(List.of(existingTeacher)));

        // Act
        Result<Void> result = groupValidationService.validateNotDuplicatedTeacherInGroup(group, newTeacher);

        // Assert
        assertTrue(result.isSuccess());
    }
}
