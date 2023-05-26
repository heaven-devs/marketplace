package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.AdDto;
import ga.heaven.marketplace.dto.CreateAdDto;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.model.AdModel;
import org.springframework.stereotype.Component;

//@Mapper
//public interface AdsMapper {
@Component
public class AdsMapper {
    //AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );

    /*@Mapping(source = "id", target = "pk")
    @Mapping(target = "author", expression = "java(this.getUser().getId())")
    Ads adsModelToAds(AdsModel adsModel);*/

    public AdDto adsModelToAds(AdModel adModel){
        AdDto adDto = new AdDto();
        adDto.setAuthor(adModel.getUser().getId());
        //ads.setImage(null);
        adDto.setImage(adModel.getImage().getPath());
        adDto.setPk(adModel.getId());
        adDto.setPrice(adModel.getPrice());
        adDto.setTitle(adModel.getTitle());
        //ads.setContentType(adsModel.getContentType());
        return adDto;
    }

    public FullAdds adsModelToFullAdds(AdModel adModel) {
        if (adModel == null) {
            return null;
        }
        FullAdds fullAdds = new FullAdds();
        fullAdds.setPk(adModel.getId());
        fullAdds.setAuthorFirstName(adModel.getUser().getFirstName());
        fullAdds.setAuthorLastName(adModel.getUser().getLastName());
        fullAdds.setDescription(adModel.getDescription());
        fullAdds.setEmail(adModel.getUser().getUsername());
        fullAdds.setImage(adModel.getImage().getPath());
        fullAdds.setPhone(adModel.getUser().getPhone());
        fullAdds.setPrice((adModel.getPrice()));
        fullAdds.setTitle(adModel.getTitle());
        return fullAdds;
    }

    public AdModel CreateAdsToAdsModel(CreateAdDto createAdDto) {
        if (createAdDto == null) {
            throw new RuntimeException("Неясно что делать, если createAds == null");
        }
        AdModel adModel = new AdModel();
        adModel.setDescription(createAdDto.getDescription());
        adModel.setPrice(createAdDto.getPrice());
        adModel.setTitle(createAdDto.getTitle());
        return adModel;
    }

    public AdModel CreateAdsToAdsModel(AdModel adModel, CreateAdDto createAdDto){
        adModel.setDescription(createAdDto.getDescription());
        adModel.setPrice(createAdDto.getPrice());
        adModel.setTitle(createAdDto.getTitle());
        return adModel;
    }
}
