package ga.heaven.marketplace.dto;

import lombok.Data;

@Data
public class SetPasswordDto {
    public String currentPassword;
    public String newPassword;
}
