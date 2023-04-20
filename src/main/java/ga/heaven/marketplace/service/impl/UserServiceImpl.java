package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.SetPasswordDto;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        return null;
    }

    @Override
    public UserDto setUserPassword(SetPasswordDto passwordDto) {
        return null;
    }

    @Override
    public UserDto loadUserImage(MultipartFile image) {
        return null;
    }
}
