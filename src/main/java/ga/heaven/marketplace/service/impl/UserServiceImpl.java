package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.RegisterReq;
import ga.heaven.marketplace.dto.Role;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.mapper.UserMapper;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.UserRepository;
import ga.heaven.marketplace.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getCurrentUser(String username) {
        return mapper.mapToUserDto(getUser(username));
    }

    @Override
    public UserModel getUser(String username) {
        return repository.findUserByUsername(username).orElseThrow();
    }

    @Override
    public UserModel updateUser(UserModel authUser, UserDto userDto) {
        UserModel userModel = mapper.mapUserDtoToUserModel(userDto, authUser);
        repository.save(userModel);
        return userModel;
    }

    @Override
    public UserDto setUserPassword(UserModel authUser, NewPassword newPassword) {
        authUser.setPassword(passwordEncoder.encode(newPassword.newPassword));
        repository.save(authUser);
        return mapper.mapToUserDto(authUser);
    }

    @Override
    public UserDto loadUserImage(UserModel userModel, ImageModel image) {
        image.setId(Optional.ofNullable(userModel.getImage())
                .map(ImageModel::getId)
                .orElse(null));
        userModel.setImage(image);
        //userModel.setContentType(image.getContentType());
        repository.save(userModel);
        return mapper.mapToUserDto(userModel);
    }

    @Override
    public boolean isPasswordCorrect(UserModel authUser, String currentPassword) {
        return passwordEncoder.matches(currentPassword, authUser.getPassword());
    }

    @Override
    public void createUser(RegisterReq registerReq, Role role) {
        UserModel userModel = mapper.mapRegisterReqToUserModel(registerReq, new UserModel());
        userModel.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        userModel.setRole(Objects.requireNonNullElse(role, Role.USER));
        repository.save(userModel);
    }
}
