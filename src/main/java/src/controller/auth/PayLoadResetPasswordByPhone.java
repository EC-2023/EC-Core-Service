package src.controller.auth;

import lombok.Data;

@Data
public class PayLoadResetPasswordByPhone {
    String phone;
    String token;
    String newPassword;
}
