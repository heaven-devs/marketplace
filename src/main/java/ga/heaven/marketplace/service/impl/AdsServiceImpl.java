package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.mapper.AdsMapper;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.AdsRepository;
import ga.heaven.marketplace.service.AdsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final UserServiceImpl userService;
    
    private final AdsMapper adsMapper;
    
    public AdsServiceImpl(
            AdsRepository adsRepository, UserServiceImpl userService, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
    }
    
    public ResponseWrapperAds getAds() {
        //List<Ads> ads = new ArrayList<>();
        List<AdModel> adModels = adsRepository.findAll();
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        wrapperAds.setCount(adModels.size());
        wrapperAds.setResults(
                adModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        //return AdsMapper.INSTANCE.adsModelToAds(a);
        return wrapperAds;
    }
    
    @Override
    public AdDto addAds(CreateAdDto properties, ImageModel image, String userName) {
        AdModel adModel = adsMapper.CreateAdsToAdsModel(properties);
        adModel.setImage(image);
        adModel.setUser(userService.getUser(userName));
        return adsMapper.adsModelToAds(adsRepository.save(adModel));
    }
    
    @Override
    public FullAdds getFullAd(long id) {
        AdModel adModel = adsRepository.findById(id).orElse(null);
        return adsMapper.adsModelToFullAdds(adModel);
    }
    
    @Override
    public int removeAds(long id) {
        if (adsRepository.findById(id).isEmpty()) {
            return 204; // не найден
        } else {
            adsRepository.deleteById(id);
            return 0; // запись удалена
        }
    }
    
    @Override
    public AdDto updateAds(long id, CreateAdDto createAdDto) {
        Optional<AdModel> adsModelOptional = adsRepository.findById(id);
        if (adsModelOptional.isEmpty()) {
            return null;
        }
        AdModel adModel = adsMapper.CreateAdsToAdsModel(adsModelOptional.get(), createAdDto);
        adsRepository.save(adModel);
        return adsMapper.adsModelToAds(adModel);
    }
    
    @Override
    public ResponseWrapperAds getAdsMe(String username) {
        UserModel user = userService.getUser(username);
        List<AdModel> adModels = adsRepository.findAdsModelByUserId(user.getId());
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        wrapperAds.setCount(adModels.size());
        wrapperAds.setResults(
                adModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        return wrapperAds;
//        return adsModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    @Override
    public AdModel updateAdsImage(AdModel ad, ImageModel image) {
        image.setId(ad.getImage().getId());
        ad.setImage(image);
        return adsRepository.saveAndFlush(ad);
    }
    
    @Override
    public List<AdDto> findByTitleContainingIgnoreCase(String searchTitle) {
        List<AdModel> adModels = adsRepository.findByTitleLikeIgnoreCase("%" + searchTitle + "%");
        return adModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    @Override
    public AdModel getAdsById(long id) {
        return adsRepository.findById(id).orElse(null);
    }
}
