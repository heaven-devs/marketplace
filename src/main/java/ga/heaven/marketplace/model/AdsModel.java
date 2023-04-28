package ga.heaven.marketplace.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mp_ads")
@Data
public class AdsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // id объявления
    private String image; // ссылка на картинку объявления
    private Integer price;    // цена объявления
    private String title; // заголовок объявления

    // Посмотреть название Entity у Сергея
    /*@ManyToOne
    private UserModel user;*/
}
