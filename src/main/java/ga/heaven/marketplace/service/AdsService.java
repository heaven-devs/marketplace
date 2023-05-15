package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.dto.ResponseWrapperAds;
import ga.heaven.marketplace.model.AdsModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdsService {
    ResponseWrapperAds getAds();
    void addAds(CreateAds properties, MultipartFile image, String userName);
    
    FullAdds getFullAd(long id);

    int removeAds(long id);

    Ads updateAds(long id, CreateAds createAds);
    
    List<Ads> getAdsMe(String username);

    int updateAdsImage(int id, MultipartFile image);

    List<Ads> findByTitleContainingIgnoreCase(String searchTitle);

    AdsModel getAdsById(long id);
}
