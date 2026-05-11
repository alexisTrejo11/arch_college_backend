package io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalLineDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}