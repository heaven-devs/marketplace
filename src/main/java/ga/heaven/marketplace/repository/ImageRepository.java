package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {

}
