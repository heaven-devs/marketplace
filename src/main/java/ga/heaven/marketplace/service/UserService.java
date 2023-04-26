package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getCurrentUser();

    UserDto updateUser(UserDto userDto);

    UserDto setUserPassword(NewPassword newPassword);

    UserDto loadUserImage(MultipartFile image);
}
