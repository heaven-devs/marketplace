package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class RequestWrapperCommentDto {
    public Integer adId;
    public CommentDto data;
    public String username;
    
    public RequestWrapperCommentDto setAdId(Integer adId) {
        this.adId = adId;
        return this;
    }
    
    public RequestWrapperCommentDto setData(CommentDto data) {
        this.data = data;
        return this;
    }
    
    public RequestWrapperCommentDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
