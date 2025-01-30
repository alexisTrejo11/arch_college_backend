package microservice.academic_curriculum_service;

import microservice.common_classes.DTOs.Group.ScheduleDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.Schedule.WEEKDAY;
import microservice.schedule_service.Models.Group;
import microservice.schedule_service.Models.Schedule;
import microservice.schedule_service.Repository.GroupRepository;
import microservice.schedule_service.Repository.ScheduleRepository;
import microservice.schedule_service.Service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private ScheduleService scheduleService;
    private GroupRepository groupRepository;
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        groupRepository = Mockito.mock(GroupRepository.class);
        scheduleRepository = Mockito.mock(ScheduleRepository.class);
        scheduleService = new ScheduleService(groupRepository, scheduleRepository);
    }

    @Test
    void validateClassroomSchedule_ShouldReturnSuccess_WhenNoConflicts() {
        // Arrange
        String classroom = "A101";
        List<ScheduleDTO> schedules = List.of(new ScheduleDTO(WEEKDAY.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        Long groupId = 1L;

        when(groupRepository.findByClassroomAndSchoolPeriod(eq(classroom), anyString()))
                .thenReturn(List.of());

        // Act
        Result<Void> result = scheduleService.validateClassroomSchedule(classroom, schedules, groupId);

        // Assert
        assertTrue(result.isSuccess());
        verify(groupRepository).findByClassroomAndSchoolPeriod(eq(classroom), anyString());
    }

    @Test
    void validateClassroomSchedule_ShouldReturnError_WhenConflictDetected() {
        // Arrange
        String classroom = "A101";
        List<ScheduleDTO> schedules = List.of(new ScheduleDTO(WEEKDAY.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        Long groupId = 1L;

        Group conflictingGroup = createGroupWithSchedule(2L, WEEKDAY.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));

        when(groupRepository.findByClassroomAndSchoolPeriod(eq(classroom), anyString()))
                .thenReturn(List.of(conflictingGroup));

        // Act
        Result<Void> result = scheduleService.validateClassroomSchedule(classroom, schedules, groupId);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Schedule conflict detected: The requested schedule is already assigned to another group at the same time.",
                result.getErrorMessage());
    }

    @Test
    void validateTeacherSchedule_ShouldReturnSuccess_WhenNoConflicts() {
        // Arrange
        Long teacherId = 1L;
        List<ScheduleDTO> schedules = List.of(new ScheduleDTO(WEEKDAY.THURSDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        Long groupId = 1L;

        when(groupRepository.findByTeacherIdAndSchoolPeriod(eq(teacherId), anyString()))
                .thenReturn(List.of());

        // Act
        Result<Void> result = scheduleService.validateTeacherSchedule(teacherId, schedules, groupId);

        // Assert
        assertTrue(result.isSuccess());
        verify(groupRepository).findByTeacherIdAndSchoolPeriod(eq(teacherId), anyString());
    }

    @Test
    void validateTeachersSchedule_ShouldReturnError_WhenConflictDetectedForOneTeacher() {
        // Arrange
        Set<Long> teacherIds = Set.of(1L, 2L);
        List<ScheduleDTO> schedulesDto = List.of(new ScheduleDTO(WEEKDAY.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(15, 0)));
        Long groupId = 1L;

        Group conflictGroup = createGroupWithSchedule(3L, WEEKDAY.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(15, 0));

        when(groupRepository.findByTeacherIdAndSchoolPeriod(eq(1L), anyString())).thenReturn(List.of());
        when(groupRepository.findByTeacherIdAndSchoolPeriod(eq(2L), anyString())).thenReturn(List.of(conflictGroup));

        // Act
        Result<Void> result = scheduleService.validateTeachersSchedule(teacherIds, schedulesDto, groupId).join();

        // Assert
        assertFalse(result.isSuccess());
    }

    @Test
    void mapScheduleDTOToEntity_ShouldSaveAndReturnSchedules() {
        // Arrange
        List<ScheduleDTO> scheduleDTOs = List.of(
                new ScheduleDTO(WEEKDAY.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 0)),
                new ScheduleDTO(WEEKDAY.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))
        );

        when(scheduleRepository.findByDayAndStartTimeAndEndTime(any(WEEKDAY.class), any(), any()))
                .thenReturn(List.of());

        // Act
        List<Schedule> schedules = scheduleService.mapScheduleDTOToEntity(scheduleDTOs);

        // Assert
        assertEquals(2, schedules.size());
        verify(scheduleRepository, times(2)).saveAndFlush(any(Schedule.class));
    }

    // Utility Methods
    private Group createGroupWithSchedule(Long id, WEEKDAY day, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = new Schedule();
        schedule.setDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        Group group = new Group();
        group.setId(id);
        group.setSchedule(List.of(schedule));

        return group;
    }
}
