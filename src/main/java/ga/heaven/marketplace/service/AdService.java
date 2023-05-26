package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.AdDto;
import ga.heaven.marketplace.dto.CreateAdDto;
import ga.heaven.marketplace.dto.FullAdDto;
import ga.heaven.marketplace.dto.ResponseWrapperAdsDto;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;

import java.util.List;

public interface AdService {
    ResponseWrapperAdsDto getAds();
    AdDto addAds(CreateAdDto properties, ImageModel image, String userName);
    
    FullAdDto getFullAd(long id);

    int removeAd(long id);

    AdDto updateAd(long id, CreateAdDto createAdDto);
    
    ResponseWrapperAdsDto getAdsMe(String username);

    AdModel updateAdImage(AdModel ad, ImageModel image);
    
    List<AdDto> findByTitleContainingIgnoreCase(String searchTitle);

    AdModel getAdById(long id);
}
