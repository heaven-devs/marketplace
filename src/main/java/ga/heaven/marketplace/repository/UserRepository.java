package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
