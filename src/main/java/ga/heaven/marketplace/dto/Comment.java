package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class Comment {
    private long author;            // id автора комментария
    private String authorImage;     // ссылка на аватар автора комментария
    private String authorFirstName; // имя создателя комментария
    private long createdAt;         //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private long pk;                // id комментария
    private String text;            // текст комментария
}
