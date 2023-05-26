package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.RegisterReqDto;
import ga.heaven.marketplace.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReqDto, Role role);
}
