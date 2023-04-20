package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.SetPasswordDto;
import ga.heaven.marketplace.dto.UserDto;

public interface UserService {

    UserDto getCurrentUser();

    UserDto updateUser(UserDto user);

    UserDto setUserPassword(SetPasswordDto passwordDto);
}
