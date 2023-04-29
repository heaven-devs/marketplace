package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer author;            // id автора комментария
    private String authorImage;     // ссылка на аватар автора комментария
    private String authorFirstName; // имя создателя комментария
    private Long createdAt;         //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private Integer pk;                // id комментария
    private String text;            // текст комментария
}
