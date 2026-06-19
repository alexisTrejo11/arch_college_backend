package io.github.alexistrejo11.architecture.college.schedule.repository;

import io.github.alexistrejo11.architecture.college.common.models.schedule.WEEKDAY;
import io.github.alexistrejo11.architecture.college.schedule.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        List<Schedule> findByDayAndStartTimeAndEndTime(WEEKDAY day, LocalTime startTime, LocalTime endTime);

}
