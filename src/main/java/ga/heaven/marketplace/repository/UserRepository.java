package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
