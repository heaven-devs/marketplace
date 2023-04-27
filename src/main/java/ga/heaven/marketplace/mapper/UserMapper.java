package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.LoginReq;
import ga.heaven.marketplace.dto.RegisterReq;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto mapToUserDto(UserModel userModel) {
        UserDto dto = new UserDto();
        dto.setId(userModel.getId());
        dto.setEmail(userModel.getEmail());
        dto.setFirstName(userModel.getFirstName());
        dto.setLastName(userModel.getLastName());
        dto.setPhone(userModel.getPhone());
        dto.setImage(userModel.getImage());
        return dto;
    }

    public UserModel mapUserDtoToUserModel(UserDto userDto, UserModel userModel) {
        userModel.setId(userDto.getId());
        userModel.setEmail(userDto.getEmail());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userModel.setPhone(userDto.getPhone());
        userModel.setImage(userDto.getImage());
        return userModel;
    }

    public RegisterReq mapToRegisterReq(UserModel userModel) {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername(userModel.getUsername());
        registerReq.setPassword(userModel.getPassword());
        registerReq.setFirstName(userModel.getFirstName());
        registerReq.setLastName(userModel.getLastName());
        registerReq.setPhone(userModel.getPhone());
        registerReq.setRole(userModel.getRole());
        return registerReq;
    }

    public UserModel mapRegisterReqToUserModel(RegisterReq registerReq, UserModel userModel) {
        userModel.setUsername(registerReq.getUsername());
        userModel.setPassword(registerReq.getPassword());
        userModel.setFirstName(registerReq.getFirstName());
        userModel.setLastName(registerReq.getLastName());
        userModel.setPhone(registerReq.getPhone());
        userModel.setRole(registerReq.getRole());
        return userModel;
    }

    public LoginReq mapToUserModel(UserModel userModel) {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername(userModel.getUsername());
        loginReq.setPassword(userModel.getPassword());
        return loginReq;
    }

    public UserModel mapLoginReqToUserModel(LoginReq loginReq, UserModel userModel) {
        userModel.setUsername(loginReq.getUsername());
        userModel.setPassword(loginReq.getPassword());
        return userModel;
    }
}
