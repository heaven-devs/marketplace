package ga.heaven.marketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsDto {
    public int count;
    public List<AdDto> results;
}
