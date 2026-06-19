package io.github.alexistrejo11.architecture.college.enrollment.model.Preload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Career {

    @Id
    private Long id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;

    @JsonProperty("title_awarded")
    private String titleAwarded;

    @JsonProperty("modality")
    private String modality;

    @JsonProperty("semester_duration")
    private String semesterDuration;

    @JsonProperty("total_career_credits")
    private int totalCareerCredits;

    @JsonProperty("total_obligatory_credits")
    private int totalObligatoryCredits;

    @JsonProperty("total_elective_credits")
    private int totalElectiveCredits;
}
