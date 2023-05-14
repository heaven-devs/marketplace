package ga.heaven.marketplace.model;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mp_ads")
@Data
public class AdsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // id объявления
    private String image; // ссылка на картинку объявления
    private int price;    // цена объявления
    private String title; // заголовок объявления
    private String contentType; // тип файла image
    private String description;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> commentModels;*/
}
