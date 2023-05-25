package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.AdDto;
import ga.heaven.marketplace.dto.CreateAdDto;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.dto.ResponseWrapperAds;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;

import java.util.List;

public interface AdsService {
    ResponseWrapperAds getAds();
    AdDto addAds(CreateAdDto properties, ImageModel image, String userName);
    
    FullAdds getFullAd(long id);

    int removeAds(long id);

    AdDto updateAds(long id, CreateAdDto createAdDto);
    
    ResponseWrapperAds getAdsMe(String username);

    AdModel updateAdsImage(AdModel ad, ImageModel image);
    
    List<AdDto> findByTitleContainingIgnoreCase(String searchTitle);

    AdModel getAdsById(long id);
}
