package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.mapper.UserMapper;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.UserRepository;
import ga.heaven.marketplace.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserModel temporaryAuthorizedUser;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        temporaryAuthorizedUser = repository.findById(1L).orElse(null);
    }

    @Override
    public UserDto getCurrentUser() {
        return mapper.mapToUserDto(temporaryAuthorizedUser);
    }

    @Override
    public UserModel updateUser(UserDto userDto) {
        UserModel userModel = mapper.mapUserDtoToUserModel(userDto, temporaryAuthorizedUser);
        repository.save(userModel);
        return userModel;
    }

    @Override
    public UserDto setUserPassword(NewPassword newPassword) {
        temporaryAuthorizedUser.setPassword(passwordEncoder.encode(newPassword.newPassword));
        repository.save(temporaryAuthorizedUser);
        return mapper.mapToUserDto(temporaryAuthorizedUser);
    }

    @Override
    public UserDto loadUserImage(MultipartFile image) {
        temporaryAuthorizedUser.setImage(image.toString());
        temporaryAuthorizedUser.setContentType(image.getContentType());
        repository.save(temporaryAuthorizedUser);
        return mapper.mapToUserDto(temporaryAuthorizedUser);
    }

    @Override
    public boolean isPasswordCorrect(String currentPassword) {
        return passwordEncoder.matches(currentPassword, temporaryAuthorizedUser.getPassword());
    }
}
