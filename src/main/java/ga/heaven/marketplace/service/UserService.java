package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.RegisterReq;
import ga.heaven.marketplace.dto.Role;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getCurrentUser(String username);

    UserModel updateUser(UserModel authUser, UserDto userDto);

    UserModel getUser(String username);

    UserDto setUserPassword(UserModel authUser, NewPassword newPassword);

    UserDto loadUserImage(UserModel authUser, ImageModel image);

    boolean isPasswordCorrect(UserModel authUser, String currentPassword);

    void createUser(RegisterReq registerReq, Role role);
}
