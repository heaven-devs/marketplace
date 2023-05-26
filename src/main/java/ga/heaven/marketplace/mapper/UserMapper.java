package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.LoginReqDto;
import ga.heaven.marketplace.dto.RegisterReqDto;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {
    public UserDto mapToUserDto(UserModel userModel) {
        UserDto dto = new UserDto();
        dto.setId(userModel.getId());
        dto.setEmail(userModel.getUsername());
        dto.setFirstName(userModel.getFirstName());
        dto.setLastName(userModel.getLastName());
        dto.setPhone(userModel.getPhone());
        dto.setImage(Optional.ofNullable(userModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        return dto;
    }
    
    public UserModel mapUserDtoToUserModel(UserDto userDto) {
        return mapUserDtoToUserModel(userDto, new UserModel());
    }
    
    public UserModel mapUserDtoToUserModel(UserDto userDto, UserModel userModel) {
        userModel.setId(userDto.getId());
        userModel.setUsername(userDto.getEmail());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userModel.setPhone(userDto.getPhone());
        //userModel.setImage(userDto.getImage());
        return userModel;
    }

    public RegisterReqDto mapToRegisterReq(UserModel userModel) {
        RegisterReqDto registerReqDto = new RegisterReqDto();
        registerReqDto.setUsername(userModel.getUsername());
        registerReqDto.setPassword(userModel.getPassword());
        registerReqDto.setFirstName(userModel.getFirstName());
        registerReqDto.setLastName(userModel.getLastName());
        registerReqDto.setPhone(userModel.getPhone());
        registerReqDto.setRole(userModel.getRole());
        return registerReqDto;
    }

    public UserModel mapRegisterReqToUserModel(RegisterReqDto registerReqDto, UserModel userModel) {
        userModel.setUsername(registerReqDto.getUsername());
        userModel.setPassword(registerReqDto.getPassword());
        userModel.setFirstName(registerReqDto.getFirstName());
        userModel.setLastName(registerReqDto.getLastName());
        userModel.setPhone(registerReqDto.getPhone());
        userModel.setRole(registerReqDto.getRole());
        return userModel;
    }

    public LoginReqDto mapToUserModel(UserModel userModel) {
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername(userModel.getUsername());
        loginReqDto.setPassword(userModel.getPassword());
        return loginReqDto;
    }

    public UserModel mapLoginReqToUserModel(LoginReqDto loginReqDto, UserModel userModel) {
        userModel.setUsername(loginReqDto.getUsername());
        userModel.setPassword(loginReqDto.getPassword());
        return userModel;
    }
}
