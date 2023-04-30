package ga.heaven.marketplace.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mp_comments")
@Data
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserModel user;
    @ManyToOne
    private AdsModel ads;
    private LocalDateTime createdAt;
    private String text;
}
