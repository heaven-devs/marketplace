package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.AdsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface AdsRepository extends JpaRepository<AdsModel, Long> {
    List<AdsModel> findByTitleLike(String s);

    List<AdsModel> findAdsModelByUserId(Long id);

    List<AdsModel> findByTitleLikeIgnoreCase(String s);
}
