package src.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.auth.JwtTokenUtil;
import src.config.dto.TokenResetPasswordDto;
import src.config.exception.BadRequestException;
import src.config.exception.NotFoundException;
import src.model.User;
import src.repository.IUserRepository;
import src.service.Email.EmailService;
import src.service.SMS.SMSService;
import src.service.User.Dtos.UserProfileDto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@ApiPrefixController("/auth")
@Tag(name = "User authentication")
public class AuthController {
    private final EmailService emailService;
    private final JwtTokenUtil jwtUtil;
    private final IUserRepository userRepository;
    private final SMSService smsService;
    private final ModelMapper toDto;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    //    @Value("${jwt.secret}")
    private String ENCRYPTION_KEY = "Jf57xtfgC5X9tktm"; // Thay đổi bằng khóa bí mật 16 ký tự của bạn
    private static final String AES_ALGORITHM = "AES";
    @Value("${URL_FE}")
    private String URL;

    public AuthController(EmailService emailService, JwtTokenUtil jwtUtil, IUserRepository userRepository, SMSService smsService, ModelMapper toDto) {
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.smsService = smsService;
        this.toDto = toDto;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid LoginInputDto loginRequest) throws Exception {
        final User user = userRepository.findByEmail(loginRequest.getUsername());
        if (user == null) {
            throw new Exception("Cannot find user with email");
        }
        if (!JwtTokenUtil.comparePassword(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Password not correct");
        }
        final String accessToken = jwtUtil.generateAccessToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(user);
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return ResponseEntity.ok(new LoginDto(accessToken, refreshToken, toDto.map(user, UserProfileDto.class)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody @Valid RefreshTokenInput refreshTokenRequest) throws Exception {
        final String refreshToken = refreshTokenRequest.getRefreshToken();
        // Check if the refresh token is valid and not expired
        String username = jwtUtil.checkRefreshToken(refreshToken);
        if (username == null)
            throw new BadRequestException("Not Type Refresh Token");
        final User userDetails = userRepository.findByEmail(jwtUtil.getUsernameFromToken(refreshToken));
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
            final String accessToken = jwtUtil.generateAccessToken(userDetails);
            return ResponseEntity.ok(new RefreshTokenDto(accessToken, refreshToken));
        }
        throw new Exception("Invalid refresh token");
    }

    @PostMapping("/request-reset-password-by-email")
    public ResponseEntity<?> requestResetPasswordByEmail(@RequestParam("email") String userEmail) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        TokenResetPasswordDto tokenResetPassword = new TokenResetPasswordDto(token, userEmail, expiryDate, 0);
        setToken(user, tokenResetPassword);
        userRepository.save(user);
        String resetPasswordUrl = URL + URLEncoder.encode(user.getTokenResetPassword(), "UTF-8");
        emailService.sendResetPasswordEmail(userEmail, resetPasswordUrl);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordByToken( @RequestBody PayLoadResetPasswordByPhone input) {
        User user = userRepository.findByTokenResetPassword(input.getToken()).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto tokenResetPassword = decodeToken(user.getTokenResetPassword());
        if (tokenResetPassword == null || tokenResetPassword.isExpired()) {
            return ResponseEntity.badRequest().body("Token is expired");
        }
        if (!tokenResetPassword.getEmail().equals(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email not match!");
        }
        user.setRequestCount(0);
        user.setLastRequest(null);
        user.setHashedPassword(JwtTokenUtil.hashPassword(input.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/request-pin/{phone}")
    public void requestPin(@PathVariable("phone") String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto token = new TokenResetPasswordDto();
        token.setToken(generateRandomDigits());
        token.setEmail(user.getEmail());
        token.setExpired(LocalDateTime.now().plusMinutes(1).plusSeconds(2));
        token.setType(1);
        setToken(user, token);
        user = userRepository.save(user);
        String messageText = "Mã xác thực của bạn là: " + token.getToken();
        smsService.sendSMS(phoneNumber.replaceFirst("^0", "+84"), messageText);
    }

    @PostMapping("/validate-pin/{phone}")
    public ResponseEntity<?> validatePin(@PathVariable String phone, @RequestBody TokenDto input) throws UnsupportedEncodingException {
        User user = userRepository.findByPhoneNumber(phone).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto tokenResetPassword = decodeToken(user.getTokenResetPassword());
        if (tokenResetPassword == null || tokenResetPassword.isExpired()) {
            return ResponseEntity.badRequest().body("Token is expired");
        }
        if (!tokenResetPassword.getToken().equals(input.getToken())) {
            return ResponseEntity.badRequest().body("Pin code is not correct!");
        }
        tokenResetPassword.setExpired(LocalDateTime.now().plusMinutes(1).plusSeconds(2).plusSeconds(2));
        user.setTokenResetPassword(encodeToken(tokenResetPassword));
        userRepository.save(user);

        return ResponseEntity.ok(URL + URLEncoder.encode(user.getTokenResetPassword(), "UTF-8"));
    }

    private String generateRandomDigits() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


    private String encodeToken(TokenResetPasswordDto tokenResetPassword) {
        try {
            String jsonString = objectMapper.writeValueAsString(tokenResetPassword);
            byte[] encryptedBytes = encryptAES(jsonString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding token", e);
        }
    }

    private TokenResetPasswordDto decodeToken(String encodedToken) {
        try {
            byte[] decryptedBytes = decryptAES(Base64.getDecoder().decode(encodedToken));
            String jsonString = new String(decryptedBytes, StandardCharsets.UTF_8);
            return objectMapper.readValue(jsonString, TokenResetPasswordDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error decoding token", e);
        }
    }

    private byte[] encryptAES(byte[] data) throws Exception {
        Key key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private byte[] decryptAES(byte[] encryptedData) throws Exception {
        Key key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    private void setToken(User user, TokenResetPasswordDto token) {
        user.setTokenResetPassword(encodeToken(token));
        if (user.getRequestCount() > 3
                && (user.getLastRequest() == null || user.getLastRequest().getTime() - new Date().getTime() < 24 * 60 * 60 * 1000)) {
            throw new BadRequestException("Bạn đã gửi quá nhiều yêu cầu để cấp lại mật khẩu, hãy đợi 24h sau để thực hiện lại chức năng");
        }
        user.setRequestCount(user.getRequestCount() + 1);
        user.setLastRequest(new Date(new Date().getTime()));
    }
}