package ga.heaven.marketplace.dto;

import ga.heaven.marketplace.model.UserModel;
import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public static RegisterReq fromUserModel(UserModel userModel) {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername(userModel.getUsername());
        registerReq.setPassword(userModel.getPassword());
        registerReq.setFirstName(userModel.getFirstName());
        registerReq.setLastName(userModel.getLastName());
        registerReq.setPhone(userModel.getPhone());
        registerReq.setRole(userModel.getRole());
        return registerReq;
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUsername(this.getUsername());
        userModel.setPassword(this.getPassword());
        userModel.setFirstName(this.getFirstName());
        userModel.setLastName(this.getLastName());
        userModel.setPhone(this.getPhone());
        userModel.setRole(this.getRole());
        return userModel;
    }
}
