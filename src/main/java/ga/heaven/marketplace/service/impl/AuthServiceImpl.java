package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.RegisterReqDto;
import ga.heaven.marketplace.dto.Role;
import ga.heaven.marketplace.security.UserDetailsManagerImpl;
import ga.heaven.marketplace.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManagerImpl manager;

  private final UserServiceImpl service;

  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserDetailsManagerImpl manager, UserServiceImpl service, PasswordEncoder passwordEncoder) {
    this.manager = manager;
    this.service = service;
    this.encoder = passwordEncoder;
  }

  @Override
  public boolean login(String userName, String password) {
    if (!manager.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterReqDto registerReqDto, Role role) {
    if (manager.userExists(registerReqDto.getUsername())) {
      return false;
    }

    service.createUser(registerReqDto, role);
    return true;
  }
}
