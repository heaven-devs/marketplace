package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.mapper.AdMapper;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.AdRepository;
import ga.heaven.marketplace.service.AdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserServiceImpl userService;
    
    private final AdMapper adMapper;
    
    public AdServiceImpl(
            AdRepository adRepository, UserServiceImpl userService, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.userService = userService;
        this.adMapper = adMapper;
    }
    
    public ResponseWrapperAdsDto getAds() {
        //List<Ads> ads = new ArrayList<>();
        List<AdModel> adModels = adRepository.findAll();
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setCount(adModels.size());
        wrapperAds.setResults(
                adModels.stream()
                        .map(adMapper::adModelToAdDto)
                        .collect(Collectors.toList())
        );
        //return AdsMapper.INSTANCE.adsModelToAds(a);
        return wrapperAds;
    }
    
    @Override
    public AdDto addAds(CreateAdDto properties, ImageModel image, String userName) {
        AdModel adModel = adMapper.CreateAdsToAdsModel(properties);
        adModel.setImage(image);
        adModel.setUser(userService.getUser(userName));
        return adMapper.adModelToAdDto(adRepository.save(adModel));
    }
    
    @Override
    public FullAdDto getFullAd(long id) {
        AdModel adModel = adRepository.findById(id).orElse(null);
        return adMapper.adsModelToFullAdds(adModel);
    }
    
    @Override
    public int removeAd(long id) {
        if (adRepository.findById(id).isEmpty()) {
            return 204; // не найден
        } else {
            adRepository.deleteById(id);
            return 0; // запись удалена
        }
    }
    
    @Override
    public AdDto updateAd(long id, CreateAdDto createAdDto) {
        Optional<AdModel> adModelOptional = adRepository.findById(id);
        if (adModelOptional.isEmpty()) {
            return null;
        }
        AdModel adModel = adMapper.CreateAdsToAdsModel(adModelOptional.get(), createAdDto);
        adRepository.save(adModel);
        return adMapper.adModelToAdDto(adModel);
    }
    
    @Override
    public ResponseWrapperAdsDto getAdsMe(String username) {
        UserModel user = userService.getUser(username);
        List<AdModel> adModels = adRepository.findAdsModelByUserId(user.getId());
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setCount(adModels.size());
        wrapperAds.setResults(
                adModels.stream()
                        .map(adMapper::adModelToAdDto)
                        .collect(Collectors.toList())
        );
        return wrapperAds;
//        return adsModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    @Override
    public AdModel updateAdImage(AdModel ad, ImageModel image) {
        image.setId(ad.getImage().getId());
        ad.setImage(image);
        return adRepository.saveAndFlush(ad);
    }
    
    @Override
    public List<AdDto> findByTitleContainingIgnoreCase(String searchTitle) {
        List<AdModel> adModels = adRepository.findByTitleLikeIgnoreCase("%" + searchTitle + "%");
        return adModels.stream().map(adMapper::adModelToAdDto).collect(Collectors.toList());
    }
    
    @Override
    public AdModel getAdById(long id) {
        return adRepository.findById(id).orElse(null);
    }
}
