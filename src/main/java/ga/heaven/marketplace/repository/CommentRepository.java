package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    Optional<CommentModel> findDistinctFirstByTextEqualsAndUserEqualsAndAdsEquals(String text, UserModel user, AdModel ads);
    List<CommentModel> findAllByAds_Id(Long ads_id);
}

