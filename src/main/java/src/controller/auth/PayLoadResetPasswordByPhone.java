package src.controller.auth;

import lombok.Data;

@Data
public class PayLoadResetPasswordByPhone {
    String newPassword;
    String token;
}
