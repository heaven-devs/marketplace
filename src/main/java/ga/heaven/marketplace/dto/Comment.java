package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class Comment {
    private int author;
    private String createdAt;
    private int pk;
    private String text;
}
