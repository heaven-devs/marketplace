package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.AdDto;
import ga.heaven.marketplace.dto.CreateAdDto;
import ga.heaven.marketplace.dto.FullAdDto;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Mapper
//public interface AdsMapper {
@Component
public class AdMapper {
    //AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );

    /*@Mapping(source = "id", target = "pk")
    @Mapping(target = "author", expression = "java(this.getUser().getId())")
    Ads adsModelToAds(AdsModel adsModel);*/

    public AdDto adModelToAdDto(AdModel adModel){
        AdDto adDto = new AdDto();
        adDto.setAuthor(adModel.getUser().getId());
        //ads.setImage(null);
        //adDto.setImage(adModel.getImage().getPath());
        adDto.setImage(Optional.ofNullable(adModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        
        
        
        adDto.setPk(adModel.getId());
        adDto.setPrice(adModel.getPrice());
        adDto.setTitle(adModel.getTitle());
        //ads.setContentType(adsModel.getContentType());
        return adDto;
    }

    public FullAdDto adsModelToFullAdds(AdModel adModel) {
        if (adModel == null) {
            return null;
        }
        FullAdDto fullAdDto = new FullAdDto();
        fullAdDto.setPk(adModel.getId());
        fullAdDto.setAuthorFirstName(adModel.getUser().getFirstName());
        fullAdDto.setAuthorLastName(adModel.getUser().getLastName());
        fullAdDto.setDescription(adModel.getDescription());
        fullAdDto.setEmail(adModel.getUser().getUsername());
        //fullAdDto.setImage(adModel.getImage().getPath());
        fullAdDto.setImage(Optional.ofNullable(adModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        fullAdDto.setPhone(adModel.getUser().getPhone());
        fullAdDto.setPrice((adModel.getPrice()));
        fullAdDto.setTitle(adModel.getTitle());
        return fullAdDto;
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
        adModel.setDescription(isNull(createAdDto.getDescription()) ? adModel.getDescription() : createAdDto.getDescription());
        adModel.setPrice(isNull(createAdDto.getPrice()) ? adModel.getPrice() : createAdDto.getPrice());
        adModel.setTitle(isNull(createAdDto.getTitle()) ? adModel.getTitle() : createAdDto.getTitle());
        return adModel;
    }
    
    private Boolean isNull(Object o) {
        return Optional.ofNullable(o).isEmpty();
    }
    
    
}
