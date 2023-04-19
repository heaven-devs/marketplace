package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.RegisterReq;
import ga.heaven.marketplace.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
