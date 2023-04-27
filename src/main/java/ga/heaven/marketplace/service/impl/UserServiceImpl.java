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
    public ResponseEntity<UserDto> getCurrentUser() {
        if (null == temporaryAuthorizedUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = mapper.mapToUserDto(temporaryAuthorizedUser);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        if (null == temporaryAuthorizedUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (temporaryAuthorizedUser.getId() != userDto.getId()) {
            return ResponseEntity.notFound().build();
        }
        UserModel userModel = mapper.mapUserDtoToUserModel(userDto, temporaryAuthorizedUser);
        repository.save(userModel);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<NewPassword> setUserPassword(NewPassword newPassword) {
        if (null == temporaryAuthorizedUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!passwordEncoder.matches(newPassword.currentPassword, temporaryAuthorizedUser.getPassword())) {
            return ResponseEntity.notFound().build();
        }
        temporaryAuthorizedUser.setPassword(passwordEncoder.encode(newPassword.newPassword));
        repository.save(temporaryAuthorizedUser);
        return ResponseEntity.ok(newPassword);
    }

    @Override
    public ResponseEntity<UserDto> loadUserImage(MultipartFile image) {
        if (null == temporaryAuthorizedUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        temporaryAuthorizedUser.setImage(image.toString());
        temporaryAuthorizedUser.setContentType(image.getContentType());
        repository.save(temporaryAuthorizedUser);
        return ResponseEntity.ok().build();
    }
}
