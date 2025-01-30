package microservice.user_service.Service;

import microservice.common_classes.DTOs.User.LoginDTO;
import microservice.common_classes.DTOs.User.SignupDTO;
import microservice.common_classes.DTOs.User.UserDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.user_service.Utils.JWTResponseDTO;


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
