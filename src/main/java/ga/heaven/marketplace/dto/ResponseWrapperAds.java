package ga.heaven.marketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    public int count;
    public List<Ads> results;
}
