package io.github.alexistrejo11.architecture.college.account.service;

import io.github.alexistrejo11.architecture.college.common.dto.User.ProfileDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.SignupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.UserDTO;


public interface UserService {
    UserDTO createUser(SignupDTO signupDTO, String roleName);
    UserDTO getUserById(Long userId);
    void addMemberRelationAsync(String username);
    ProfileDTO getProfileDataByUsername(String username);
}
