package ga.heaven.marketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperCommentDto {
    public int count;
    public List<CommentDto> results;
}
