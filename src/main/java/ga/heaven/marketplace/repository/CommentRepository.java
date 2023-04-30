package ga.heaven.marketplace.repository;

import ga.heaven.marketplace.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findAllByAds_Id(Long ads_id);
}

