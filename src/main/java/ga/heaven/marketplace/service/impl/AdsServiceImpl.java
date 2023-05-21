package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.mapper.AdsMapper;
import ga.heaven.marketplace.model.AdsModel;
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
        List<AdsModel> adsModels = adsRepository.findAll();
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        wrapperAds.setCount(adsModels.size());
        wrapperAds.setResults(
                adsModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        //return AdsMapper.INSTANCE.adsModelToAds(a);
        return wrapperAds;
    }
    
    @Override
    public Ads addAds(CreateAds properties, ImageModel image, String userName) {
        AdsModel adsModel = adsMapper.CreateAdsToAdsModel(properties);
        adsModel.setImage(image);
        adsModel.setUser(userService.getUser(userName));
        return adsMapper.adsModelToAds(adsRepository.save(adsModel));
    }
    
    @Override
    public FullAdds getFullAd(long id) {
        AdsModel adsModel = adsRepository.findById(id).orElse(null);
        return adsMapper.adsModelToFullAdds(adsModel);
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
    public Ads updateAds(long id, CreateAds createAds) {
        Optional<AdsModel> adsModelOptional = adsRepository.findById(id);
        if (adsModelOptional.isEmpty()) {
            return null;
        }
        AdsModel adsModel = adsMapper.CreateAdsToAdsModel(adsModelOptional.get(), createAds);
        adsRepository.save(adsModel);
        return adsMapper.adsModelToAds(adsModel);
    }
    
    @Override
    public ResponseWrapperAds getAdsMe(String username) {
        UserModel user = userService.getUser(username);
        List<AdsModel> adsModels = adsRepository.findAdsModelByUserId(user.getId());
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        wrapperAds.setCount(adsModels.size());
        wrapperAds.setResults(
                adsModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        return wrapperAds;
//        return adsModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    @Override
    public AdsModel updateAdsImage(AdsModel ad, ImageModel image) {
        image.setId(ad.getImage().getId());
        ad.setImage(image);
        return adsRepository.saveAndFlush(ad);
    }
    
    @Override
    public List<Ads> findByTitleContainingIgnoreCase(String searchTitle) {
        List<AdsModel> adsModels = adsRepository.findByTitleLikeIgnoreCase("%" + searchTitle + "%");
        return adsModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    @Override
    public AdsModel getAdsById(long id) {
        return adsRepository.findById(id).orElse(null);
    }
}
