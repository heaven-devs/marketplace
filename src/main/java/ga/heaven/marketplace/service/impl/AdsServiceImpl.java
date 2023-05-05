package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.mapper.AdsMapper;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.repository.AdsRepository;
import ga.heaven.marketplace.service.AdsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    
    public AdsServiceImpl(
            AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }
    
    
    public List<Ads> getAds() {
        //List<Ads> ads = new ArrayList<>();
         List<AdsModel> adsModels = adsRepository.findAll();
        //return AdsMapper.INSTANCE.adsModelToAds(a);
        return adsModels.stream().map(AdsMapper::adsModelToAds).collect(Collectors.toList());
    }
    
    public void addAds(CreateAds properties, MultipartFile image) {
        AdsModel adsModel = AdsMapper.CreateAdsToAdsModel(properties);
        // ДОБАВИТЬ ЛОГИКУ ПО IMAGE и по USER
        adsRepository.save(adsModel);
    }
    
    @Override
    public FullAdds getFullAd(long id) {
        AdsModel adsModel = adsRepository.findById(id).orElse(null);
        return AdsMapper.adsModelToFullAdds(adsModel);
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
    public int updateAds(long id, CreateAds createAds) {
        Optional <AdsModel> adsModelOptional = adsRepository.findById(id);
        if (adsModelOptional.isEmpty()) {
            return 404;
        }
        //AdsModel adsModel = AdsMapper.CreateAdsToAdsModel(adsRepository.getById(id), createAds);
        AdsModel adsModel = AdsMapper.CreateAdsToAdsModel(adsModelOptional.get(), createAds);
        adsRepository.save(adsModel);
        return 0;
    }
    
    @Override
    public List<Ads> getAdsMe() {
        return null;
    }
    
    @Override
    public int updateAdsImage(int id, MultipartFile image) {
        return 0;
    }

    @Override
    public List<Ads> findByTitleContainingIgnoreCase(String searchTitle) {
        List<AdsModel> adsModels = adsRepository.findByTitleLikeIgnoreCase("%"+searchTitle+"%");
        return adsModels.stream().map(AdsMapper::adsModelToAds).collect(Collectors.toList());
    }
}
