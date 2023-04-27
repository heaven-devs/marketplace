package ga.heaven.marketplace.dto;

import ga.heaven.marketplace.model.UserModel;
import lombok.Data;

@Data
public class LoginReq {
    private String password;
    private String username;

    public static LoginReq toUserModel(UserModel userModel) {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername(userModel.getUsername());
        loginReq.setPassword(userModel.getPassword());
        return loginReq;
    }

    public UserModel fromUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUsername(this.getUsername());
        userModel.setPassword(this.getPassword());
        return userModel;
    }
}
