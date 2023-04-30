package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findUserByUsername(String username);
}
