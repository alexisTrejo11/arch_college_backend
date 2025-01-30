package microservice.common_classes.DTOs.Carrer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "CareerDTO",
        description = "Represents an academic career in Architecture with all its details",
        example = """
        {
          "id": 1,
          "key": "101",
          "name": "Architecture",
          "titleAwarded": "Bachelor of Architecture",
          "modality": "In-person",
          "semesterDuration": "10 semesters",
          "totalCareerCredits": 200,
          "totalObligatoryCredits": 160,
          "totalElectiveCredits": 40,
          "careerDirectorId": 202
        }"""
)
public class CareerDTO {

    @JsonProperty("id")
    @Schema(
            description = "Unique identifier of the career",
            example = "1",
            minimum = "1"
    )
    private Long id;

    @JsonProperty("key")
    @Schema(
            description = "Unique institutional key/code for the career",
            example = "ARQ-2023"
    )
    private String key;

    @JsonProperty("name")
    @Schema(
            description = "Official name of the career",
            example = "Architecture",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @JsonProperty("title_awarded")
    @Schema(
            description = "Academic title awarded upon completion",
            example = "Bachelor of Architecture"
    )
    private String titleAwarded;

    @JsonProperty("modality")
    @Schema(
            description = "Study modality (In-person, Online, Hybrid)",
            example = "In-person",
            allowableValues = {"In-person", "Online", "Hybrid"}
    )
    private String modality;

    @JsonProperty("semester_duration")
    @Schema(
            description = "Total duration in academic semesters",
            example = "10 semesters"
    )
    private String semesterDuration;

    @JsonProperty("total_career_credits")
    @Schema(
            description = "Total credits required to complete the career",
            example = "200",
            minimum = "0"
    )
    private int totalCareerCredits;

    @JsonProperty("total_obligatory_credits")
    @Schema(
            description = "Credits from mandatory subjects (e.g., Design, Structures)",
            example = "160",
            minimum = "0"
    )
    private int totalObligatoryCredits;

    @JsonProperty("total_elective_credits")
    @Schema(
            description = "Credits from elective subjects (e.g., Urban Planning, Sustainable Design)",
            example = "40",
            minimum = "0"
    )
    private int totalElectiveCredits;

    @JsonProperty("career_director_id")
    @Schema(
            description = "ID of the academic director responsible for the career",
            example = "202",
            minimum = "1"
    )
    private Long careerDirectorId;
}