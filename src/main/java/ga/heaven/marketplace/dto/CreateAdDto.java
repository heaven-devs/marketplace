package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class CreateAdDto {
    private String description; // описание объявления
    private int price;          // цена объявления
    private String title;       // заголовок объявления
}
