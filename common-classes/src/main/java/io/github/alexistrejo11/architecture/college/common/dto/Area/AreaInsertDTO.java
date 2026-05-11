package io.github.alexistrejo11.architecture.college.common.dto.Area;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaInsertDTO {

    @JsonProperty("name")
    @NotBlank(message = "name can't be null")
    @NotBlank(message = "name can't be empty")
    private String name;
}