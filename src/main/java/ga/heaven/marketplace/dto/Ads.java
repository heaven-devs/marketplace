package ga.heaven.marketplace.dto;

import lombok.Data;

// Объявление
@Data
public class Ads {
    private long author;   // id автора объявления
    private String image; // ссылка на картинку объявления
    private long pk;       // id объявления
    private int price;    // цена объявления
    private String title; // заголовок объявления
    private String contentType; // тип файла image
}
