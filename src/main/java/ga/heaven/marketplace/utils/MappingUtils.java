package ga.heaven.marketplace.utils;

import ga.heaven.marketplace.dto.LoginReq;
import ga.heaven.marketplace.dto.RegisterReq;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public UserDto mapToUserDto(UserEntity userEntity) {
        UserDto dto = new UserDto();
        dto.setId(userEntity.getId());
        dto.setEmail(userEntity.getEmail());
        dto.setFirstName(userEntity.getFirstName());
        dto.setLastName(userEntity.getLastName());
        dto.setPhone(userEntity.getPhone());
        dto.setImage(userEntity.getImage());
        return dto;
    }

    public UserEntity mapUserDtoToUserEntity(UserDto userDto) {
        UserEntity entity = new UserEntity();
        entity.setId(userDto.getId());
        entity.setEmail(userDto.getEmail());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setPhone(userDto.getPhone());
        entity.setImage(userDto.getImage());
        return entity;
    }

    public RegisterReq mapToRegisterReq(UserEntity userEntity) {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername(userEntity.getUsername());
        registerReq.setPassword(userEntity.getPassword());
        registerReq.setFirstName(userEntity.getFirstName());
        registerReq.setLastName(userEntity.getLastName());
        registerReq.setPhone(userEntity.getPhone());
        registerReq.setRole(userEntity.getRole());
        return registerReq;
    }

    public UserEntity mapRegisterReqToUserEntity(RegisterReq registerReq) {
        UserEntity entity = new UserEntity();
        entity.setUsername(registerReq.getUsername());
        entity.setPassword(registerReq.getPassword());
        entity.setFirstName(registerReq.getFirstName());
        entity.setLastName(registerReq.getLastName());
        entity.setPhone(registerReq.getPhone());
        entity.setRole(registerReq.getRole());
        return entity;
    }

    public LoginReq mapToUserEntity(UserEntity userEntity) {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername(userEntity.getUsername());
        loginReq.setPassword(userEntity.getPassword());
        return loginReq;
    }

    public UserEntity mapLoginReqToUserEntity(LoginReq loginReq) {
        UserEntity entity = new UserEntity();
        entity.setUsername(loginReq.getUsername());
        entity.setPassword(loginReq.getPassword());
        return entity;
    }



}
