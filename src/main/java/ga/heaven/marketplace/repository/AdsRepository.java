package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.AdsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends JpaRepository<AdsModel, Long> {
}
