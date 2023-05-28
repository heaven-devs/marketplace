package ga.heaven.marketplace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mp_ads")
@Data
public class AdModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // id объявления
    //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.SAVE_UPDATE}
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "img_id")
    private ImageModel image; // ссылка на картинку объявления
    private int price;    // цена объявления
    private String title; // заголовок объявления
    //private String contentType; // тип файла image
    private String description;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> commentModels;
}
