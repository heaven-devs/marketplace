package ga.heaven.marketplace.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mp_comments")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private AdsModel ads;
    private LocalDateTime createdAt;
    private String text;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentModel that = (CommentModel) o;
        return Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(ads.getId(), that.ads.getId()) &&
                Objects.equals(text, that.text);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), ads.getId(), text);
    }
}
