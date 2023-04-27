package ga.heaven.marketplace.dto;

import ga.heaven.marketplace.model.UserModel;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;

    public static UserDto fromUserModel(UserModel userModel) {
        UserDto dto = new UserDto();
        dto.setId(userModel.getId());
        dto.setEmail(userModel.getEmail());
        dto.setFirstName(userModel.getFirstName());
        dto.setLastName(userModel.getLastName());
        dto.setPhone(userModel.getPhone());
        dto.setImage(userModel.getImage());
        return dto;
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setId(this.getId());
        userModel.setEmail(this.getEmail());
        userModel.setFirstName(this.getFirstName());
        userModel.setLastName(this.getLastName());
        userModel.setPhone(this.getPhone());
        userModel.setImage(this.getImage());
        return userModel;
    }
}