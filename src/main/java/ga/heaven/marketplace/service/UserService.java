package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getCurrentUser();

    UserModel updateUser(UserDto userDto);

    UserDto setUserPassword(NewPassword newPassword);

    UserDto loadUserImage(MultipartFile image);

    boolean isPasswordCorrect(String currentPassword);
}
