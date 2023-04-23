package src.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
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
    public ResponseEntity<?> requestPasswordResetByEmail(@RequestParam("email") String userEmail) throws MessagingException {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        TokenResetPasswordDto tokenResetPassword = new TokenResetPasswordDto(token, userEmail, expiryDate, 0);
        user.setTokenResetPassword(encodeToken(tokenResetPassword));
        user = userRepository.save(user);
        String resetPasswordUrl = "http://localhost:8080/reset-password-by-email?token=" + tokenResetPassword;
        emailService.sendResetPasswordEmail(userEmail, resetPasswordUrl);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/reset-password-by-email/{token}")
    public ResponseEntity<?> resetPasswordByEmail(@PathVariable String token, @RequestBody String newPassword) {
        User user = userRepository.findByTokenResetPassword(token).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto tokenResetPassword = decodeToken(user.getTokenResetPassword());
        if (tokenResetPassword == null || tokenResetPassword.isExpired()) {
            return ResponseEntity.badRequest().body("Token is expired");
        }
        if (!tokenResetPassword.getEmail().equals(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email not match!");
        }
        user.setHashedPassword(JwtTokenUtil.hashPassword(newPassword));
        return ResponseEntity.ok(true);
    }

    @PostMapping("/request-reset-password-by-phone")
    public void requestPasswordResetByPhone(@RequestParam("phone") String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto token = new TokenResetPasswordDto();
        token.setToken(generateRandomDigits());
        token.setEmail(user.getEmail());
        token.setExpire(LocalDateTime.now().plusMinutes(5));
        token.setType(1);
        user.setTokenResetPassword(encodeToken(token));
        user = userRepository.save(user);
        String messageText = "Mã xác thực của bạn là: " + token.getToken();
        smsService.sendSMS(phoneNumber.replaceFirst("^0", "+84"), messageText);
    }

    @PostMapping("/reset-password-by-email/{email}")
    public ResponseEntity<?> resetPasswordByPhone(@PathVariable String email, @RequestBody PayLoadResetPasswordByPhone payload) {
        User user = userRepository.findByPhoneNumber(payload.getPhone()).orElseThrow(() -> new NotFoundException("User not found"));
        TokenResetPasswordDto tokenResetPassword = decodeToken(user.getTokenResetPassword());
        if (tokenResetPassword == null || tokenResetPassword.isExpired()) {
            return ResponseEntity.badRequest().body("Token is expired");
        }
        if (!tokenResetPassword.getToken().equals(payload.getToken())) {
            return ResponseEntity.badRequest().body("Pin code is not correct!");
        }
        user.setHashedPassword(JwtTokenUtil.hashPassword(payload.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(true);
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
            return Base64.getEncoder().encodeToString(jsonString.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error encoding token", e);
        }
    }

    private TokenResetPasswordDto decodeToken(String encodedToken) {
        try {
            String jsonString = new String(Base64.getDecoder().decode(encodedToken), StandardCharsets.UTF_8);
            return objectMapper.readValue(jsonString, TokenResetPasswordDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error decoding token", e);
        }
    }

}