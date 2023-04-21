package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getCurrentUser();

    User updateUser(User user);

    User setUserPassword(NewPassword newPassword);

    User loadUserImage(MultipartFile image);
}
