package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<UserDto> getCurrentUser();

    ResponseEntity<UserDto> updateUser(UserDto userDto);

    ResponseEntity<NewPassword> setUserPassword(NewPassword newPassword);

    ResponseEntity<UserDto> loadUserImage(MultipartFile image);
}
