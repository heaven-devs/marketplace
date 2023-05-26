package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.NewPasswordDto;
import ga.heaven.marketplace.dto.RegisterReqDto;
import ga.heaven.marketplace.dto.Role;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;

public interface UserService {

    UserDto getCurrentUser(String username);

    /**
     * Update current authorized user
     * @param authUser current authorized user
     * @param userDto new user data
     * @return updated user
     */
    UserModel updateUser(UserModel authUser, UserDto userDto);

    /**
     * Get user entity by username
     * @param username user name
     * @return user entity
     */
    UserModel getUser(String username);

    /**
     * Set password for current authorized user
     * @param authUser current authorized user
     * @param newPasswordDto new password
     * @return updated user
     */
    UserDto setUserPassword(UserModel authUser, NewPasswordDto newPasswordDto);

    /**
     * Load new user image (avatar)
     * @param authUser current authorized user
     * @param image new image
     * @return updated user
     */
    UserDto loadUserImage(UserModel authUser, ImageModel image);

    /**
     * Password validation check
     * @param authUser current authorized user
     * @param currentPassword current user password
     * @return true if the password is correct
     */
    boolean isPasswordCorrect(UserModel authUser, String currentPassword);

    /**
     * Create new user
     * @param registerReqDto new user details
     * @param role role for new user
     */
    void createUser(RegisterReqDto registerReqDto, Role role);
}
