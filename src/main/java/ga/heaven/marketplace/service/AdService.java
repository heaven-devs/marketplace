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

    /**
     * Create new ad and add it to the repository
     * @param properties data for ad
     * @param image picture with ad image
     * @param userName ad creator username
     */
    AdDto addAds(CreateAdDto properties, ImageModel image, String userName);

    /**
     * Get full information about ad
     * @param id ad's id
     * @return full info about ad
     */
    FullAdDto getFullAd(long id);

    /**
     * Remove ad by id
     * @param id ad's id
     * @return http code
     */
    int removeAd(long id);

    /**
     * Update ad by id
     * @param id ad's id
     * @param createAdDto new data for update ad
     * @return updated ad
     */
    AdDto updateAd(long id, CreateAdDto createAdDto);

    /**
     * Get list of ads by user
     * @param username user name
     * @return list of user ads
     */
    ResponseWrapperAdsDto getAdsMe(String username);

    /**
     * Update image for ad
     * @param ad editing ad
     * @param image new image for ad
     * @return edited ad
     */
    AdModel updateAdImage(AdModel ad, ImageModel image);

    /**
     * Find ad, which containing string in title
     * @param searchTitle search string
     * @return list of eligible ad
     */
    List<AdDto> findByTitleContainingIgnoreCase(String searchTitle);

    /**
     * Get ad by id
     * @param id ad's id
     * @return eligible ad
     */
    AdModel getAdById(long id);
}
