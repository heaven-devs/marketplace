package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.model.AdsModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdsService {
    List<Ads> getAds();
    void addAds(CreateAds properties, MultipartFile image);
    
    ResponseWrapperComment getComments(Long id);
    
    CommentDto addComments(Integer id, CommentDto comment, String username);
    
    FullAdds getFullAd(int id);

    int removeAds(int id);

    int updateAds(int id, CreateAds createAds);

    Optional<CommentDto> deleteComments(int adId, int id);

    Optional<CommentDto> updateComments(int adId, int commentId);

    List<Ads> getAdsMe();

    int updateAdsImage(int id, MultipartFile image);
}
