package ga.heaven.marketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    public int count;
    public List<CommentDto> results;
}
