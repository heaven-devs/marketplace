package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdsService {
    List<Ads> getAds();
    void addAds(CreateAds properties, MultipartFile image);
    
    FullAdds getFullAd(long id);

    int removeAds(long id);

    int updateAds(long id, CreateAds createAds);
    
    List<Ads> getAdsMe();

    int updateAdsImage(int id, MultipartFile image);

    List<Ads> findByTitleContainingIgnoreCase(String searchTitle);
}
