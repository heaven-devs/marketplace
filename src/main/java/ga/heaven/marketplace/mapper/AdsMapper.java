package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.model.AdsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );

    @Mapping(source = "id", target = "pk")
    //@Mapping(target = "pk", expression = user.getId()) // Узнать у Сергея название Entity для User
    Ads AdsModelToAds(AdsModel adsModel);
}
