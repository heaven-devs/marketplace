package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.Ads;
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
        ads.setContentType(adsModel.getContentType());
        return ads;
    }
}
