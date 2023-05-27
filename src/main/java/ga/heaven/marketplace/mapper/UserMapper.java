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

    /**
     * Mapping user model to user dto
     * @param userModel original model
     * @return resulting user dto
     */
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

    /**
     * Mapping user dto to user model
     * @param userDto original dto
     * @param userModel user model template
     * @return resulting user model
     */
    public UserModel mapUserDtoToUserModel(UserDto userDto, UserModel userModel) {
        userModel.setId(userDto.getId());
        userModel.setUsername(userDto.getEmail());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userModel.setPhone(userDto.getPhone());
        return userModel;
    }

    /**
     * Mapping registerReqDto to user model
     * @param registerReqDto original dto
     * @param userModel user model template
     * @return resulting user model
     */
    public UserModel mapRegisterReqToUserModel(RegisterReqDto registerReqDto, UserModel userModel) {
        userModel.setUsername(registerReqDto.getUsername());
        userModel.setPassword(registerReqDto.getPassword());
        userModel.setFirstName(registerReqDto.getFirstName());
        userModel.setLastName(registerReqDto.getLastName());
        userModel.setPhone(registerReqDto.getPhone());
        userModel.setRole(registerReqDto.getRole());
        return userModel;
    }

}
