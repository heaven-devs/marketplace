package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.SetPasswordDto;
import ga.heaven.marketplace.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getCurrentUser();

    UserDto updateUser(UserDto user);

    UserDto setUserPassword(SetPasswordDto passwordDto);

    UserDto loadUserImage(MultipartFile image);
}
