package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.AdModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface AdsRepository extends JpaRepository<AdModel, Long> {
    List<AdModel> findByTitleLike(String s);

    List<AdModel> findAdsModelByUserId(Long id);

    List<AdModel> findByTitleLikeIgnoreCase(String s);
}
