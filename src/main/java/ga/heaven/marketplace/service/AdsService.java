package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdsService {
    List<Ads> getAds();
    void addAds(CreateAds properties, MultipartFile image);

    List<CommentDto> getComments(int id);

    CommentDto addComments(int id, CommentDto comment);

    FullAdds getFullAd(int id);

    int removeAds(int id);

    int updateAds(int id, CreateAds createAds);

    Optional<CommentDto> deleteComments(int adId, int id);

    Optional<CommentDto> updateComments(int adId, int commentId);

    List<Ads> getAdsMe();

    int updateAdsImage(int id, MultipartFile image);
}
