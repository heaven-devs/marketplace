package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPasswordDto;
import ga.heaven.marketplace.dto.RegisterReqDto;
import ga.heaven.marketplace.dto.Role;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;

public interface UserService {

    UserDto getCurrentUser(String username);

    UserModel updateUser(UserModel authUser, UserDto userDto);

    UserModel getUser(String username);

    UserDto setUserPassword(UserModel authUser, NewPasswordDto newPasswordDto);

    UserDto loadUserImage(UserModel authUser, ImageModel image);

    boolean isPasswordCorrect(UserModel authUser, String currentPassword);

    void createUser(RegisterReqDto registerReqDto, Role role);
}
