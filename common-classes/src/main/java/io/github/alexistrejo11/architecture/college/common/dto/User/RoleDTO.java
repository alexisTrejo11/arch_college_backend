package io.github.alexistrejo11.architecture.college.common.dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RoleDTO {
    @JsonProperty("name")
    private String name;
}