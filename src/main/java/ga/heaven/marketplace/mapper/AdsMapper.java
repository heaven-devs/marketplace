package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.model.AdsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper
//public interface AdsMapper {
public class AdsMapper {
    //AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );

    /*@Mapping(source = "id", target = "pk")
    @Mapping(target = "author", expression = "java(this.getUser().getId())")
    Ads adsModelToAds(AdsModel adsModel);*/

    public static Ads adsModelToAds(AdsModel adsModel){
        Ads ads = new Ads();
        ads.setAuthor(adsModel.getUser().getId());
        ads.setImage(adsModel.getImage());
        ads.setPk(adsModel.getId());
        ads.setPrice(adsModel.getPrice());
        ads.setTitle(adsModel.getTitle());
        //ads.setContentType(adsModel.getContentType());
        return ads;
    }

    public static FullAdds adsModelToFullAdds(AdsModel adsModel) {
        if (adsModel == null) {
            return null;
        }
        FullAdds fullAdds = new FullAdds();
        fullAdds.setPk(adsModel.getId());
        fullAdds.setAuthorFirstName(adsModel.getUser().getFirstName());
        fullAdds.setAuthorLastName(adsModel.getUser().getLastName());
        fullAdds.setDescription(adsModel.getDescription());
        fullAdds.setEmail(adsModel.getUser().getEmail());
        fullAdds.setImage(adsModel.getImage());
        fullAdds.setPhone(adsModel.getUser().getPhone());
        fullAdds.setPrice((adsModel.getPrice()));
        fullAdds.setTitle(adsModel.getTitle());
        return fullAdds;
    }

    public static AdsModel CreateAdsToAdsModel(CreateAds createAds) {
        if (createAds == null) {
            throw new RuntimeException("Неясно что делать, если createAds == null");
        }
        AdsModel adsModel = new AdsModel();
        adsModel.setDescription(createAds.getDescription());
        adsModel.setPrice(createAds.getPrice());
        adsModel.setTitle(createAds.getTitle());
        return adsModel;
    }

    public static AdsModel CreateAdsToAdsModel(AdsModel adsModel, CreateAds createAds){
        adsModel.setDescription(createAds.getDescription());
        adsModel.setPrice(createAds.getPrice());
        adsModel.setTitle(createAds.getTitle());
        return adsModel;
    }
}
