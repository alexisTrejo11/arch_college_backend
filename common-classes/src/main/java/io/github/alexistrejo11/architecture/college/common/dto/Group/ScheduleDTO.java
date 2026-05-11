package io.github.alexistrejo11.architecture.college.common.dto.Group;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.schedule.WEEKDAY;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleDTO {
        @Enumerated(EnumType.STRING)
        @NotNull(message = "day can't be null")
        private WEEKDAY day;

        @JsonProperty("start_time")
        @NotNull(message = "start_time can't be null")
        private LocalTime startTime;

        @JsonProperty("end_time")
        @NotNull(message = "end_time can't be null")
        private LocalTime endTime;

        public ScheduleDTO(WEEKDAY day, LocalTime startTime, LocalTime endTime) {
                this.day = day;
                this.startTime = startTime;
                this.endTime = endTime;
        }
}
