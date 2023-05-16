package ga.heaven.marketplace.model;

import ga.heaven.marketplace.dto.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mp_users")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "seq_users",  allocationSize=1)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
    private String contentType;
}