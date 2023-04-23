package src.config.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenResetPasswordDto {
    private String token;
    private String email;
    private LocalDateTime expire;
    private int type;

    public TokenResetPasswordDto(String token, String userEmail, LocalDateTime expiryDate, int i) {
        this.token = token;
        this.email = userEmail;
        this.expire = expiryDate;
        this.type = i;
    }

    public TokenResetPasswordDto() {

    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expire);
    }
}
