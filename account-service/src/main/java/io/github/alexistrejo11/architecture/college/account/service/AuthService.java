package io.github.alexistrejo11.architecture.college.account.service;

import io.github.alexistrejo11.architecture.college.common.dto.User.LoginDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.SignupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.User.UserDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.account.controller.dto.JWTResponseDTO;


public interface AuthService {
    Result<Void> validateSignupCredentials(SignupDTO signupDTO);
    Result<UserDTO> validateLoginCredentials(LoginDTO loginDTO);
    Result<Void> validateExistingMember(String accountNumber);
    JWTResponseDTO processLogin(UserDTO userDTO);
    JWTResponseDTO proccesSingup(UserDTO userDTO);
    Result<Void> validatePasswordFormat(String password);
    Result<Void> validateTeacher(String accountNumber);
    Result<Void> validateStudent(String accountNumber);

}
