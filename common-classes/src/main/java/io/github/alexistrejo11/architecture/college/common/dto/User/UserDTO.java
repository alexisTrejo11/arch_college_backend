package io.github.alexistrejo11.architecture.college.common.dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("student_id")
    private Integer studentId;

    @JsonProperty("teacher_id")
    private Integer teacherId;

    private List<RoleDTO> roles;
}