package microservice.schedule_service.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.common_classes.DTOs.Group.ScheduleDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.Schedule.AcademicData;
import microservice.schedule_service.Models.Group;
import microservice.schedule_service.Models.Schedule;
import microservice.schedule_service.Repository.GroupRepository;
import microservice.schedule_service.Repository.ScheduleRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;
    private static final String CURRENT_SEMESTER = AcademicData.getCurrentSchoolPeriod();

    public Result<Void> validateClassroomSchedule(String classroom, List<ScheduleDTO> schedules, Long groupId) {
        List<Group> existingGroups = groupRepository.findByClassroomAndSchoolPeriod(classroom, CURRENT_SEMESTER)
                .stream()
                .filter(group -> !Objects.equals(group.getId(), groupId))
                .toList();

        return validateScheduleConflicts(schedules, existingGroups,
                String.format("Schedule conflict: Classroom %s is already assigned for the requested time slot", classroom));
    }

    public Result<Void> validateTeacherSchedule(Long teacherId, List<ScheduleDTO> schedules, Long groupId) {
        List<Group> currentTeacherGroups = groupRepository.findByTeacherIdAndSchoolPeriod(teacherId, CURRENT_SEMESTER)
                .stream()
                .filter(group -> !Objects.equals(group.getId(), groupId))
                .toList();

        return validateScheduleConflicts(schedules, currentTeacherGroups,
                String.format("Schedule conflict: Teacher %d is already assigned for the requested time slot", teacherId));
    }

    public Result<Void> validateTeachersSchedule(Set<Long> teacherIds, List<ScheduleDTO> schedules, Long groupId) {
        for (Long teacherId : teacherIds) {
            Result<Void> result = validateTeacherSchedule(teacherId, schedules, groupId);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return Result.success();
    }

    @Transactional
    public List<Schedule> mapScheduleDTOToEntity(List<ScheduleDTO> scheduleDTOs) {
        return scheduleDTOs.stream()
                .map(this::getOrCreateSchedule)
                .toList();
    }

    private Schedule getOrCreateSchedule(ScheduleDTO scheduleDTO) {
        return scheduleRepository.findByDayAndStartTimeAndEndTime(
                        scheduleDTO.getDay(),
                        scheduleDTO.getStartTime(),
                        scheduleDTO.getEndTime())
                .stream()
                .findFirst()
                .orElseGet(() -> createNewSchedule(scheduleDTO));
    }

    private Schedule createNewSchedule(ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule(
                scheduleDTO.getDay(),
                scheduleDTO.getStartTime(),
                scheduleDTO.getEndTime());
        return scheduleRepository.saveAndFlush(newSchedule);
    }

    private Result<Void> validateScheduleConflicts(List<ScheduleDTO> newSchedules, List<Group> existingGroups, String errorMessage) {
        boolean hasConflict = newSchedules.stream()
                .anyMatch(newSchedule -> hasScheduleConflict(newSchedule, existingGroups));

        return hasConflict ? Result.error(errorMessage) : Result.success();
    }

    private boolean hasScheduleConflict(ScheduleDTO newSchedule, List<Group> existingGroups) {
        return existingGroups.stream()
                .anyMatch(group -> isConflictWithGroupSchedule(newSchedule, group.getSchedule()));
    }

    private boolean isConflictWithGroupSchedule(ScheduleDTO newSchedule, List<Schedule> existingSchedules) {
        return existingSchedules.stream()
                .anyMatch(existingSchedule ->
                        isSameDay(newSchedule, existingSchedule) &&
                                isTimeOverlapping(newSchedule, existingSchedule));
    }

    private boolean isSameDay(ScheduleDTO newSchedule, Schedule existingSchedule) {
        return existingSchedule.getDay().equals(newSchedule.getDay());
    }

    private boolean isTimeOverlapping(ScheduleDTO newSchedule, Schedule existingSchedule) {
        return isTimeRangeOverlapping(
                newSchedule.getStartTime(),
                newSchedule.getEndTime(),
                existingSchedule.getTimeRange().start(),
                existingSchedule.getTimeRange().end()
        );
    }

    private boolean isTimeRangeOverlapping(LocalTime newStart, LocalTime newEnd,
                                           LocalTime existingStart, LocalTime existingEnd) {
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }
}