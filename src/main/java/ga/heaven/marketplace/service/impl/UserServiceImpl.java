package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.User;
import ga.heaven.marketplace.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User setUserPassword(NewPassword newPassword) {
        return null;
    }

    @Override
    public User loadUserImage(MultipartFile image) {
        return null;
    }
}
