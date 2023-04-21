package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.Comment;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdsService {
    public List<Ads> getAds();
    public void addAds(CreateAds properties, MultipartFile image);

    List<Comment> getComments(int id);

    Comment addComments(int id, Comment comment);

    FullAdds getFullAd(int id);

    int removeAds(int id);

    int updateAds(int id, CreateAds createAds);

    //Comment getComments(int adId, int id);

    Optional<Comment> deleteComments(int adId, int id);

    Optional<Comment> updateComments(int adId, int commentId);

    List<Ads> getAdsMe();

    int updateAdsImage(int id, MultipartFile image);
}
