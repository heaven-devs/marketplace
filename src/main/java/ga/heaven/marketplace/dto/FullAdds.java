package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class FullAdds {
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private byte[] image;
    private String phone;
    private int pk;
    private int price;
    private String title;
}
