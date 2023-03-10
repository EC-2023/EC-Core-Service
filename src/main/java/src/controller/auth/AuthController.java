package src.controller.auth;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import src.config.JwtTokenUtil;
import src.config.annotation.ApiPrefixController;
import src.model.User;
import src.repository.IUserRepository;
import src.service.User.Dtos.UserProfileDto;

@RestController
@ApiPrefixController("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper toDto;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid LoginInputDto loginRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//        } catch (Exception e) {
//            throw new Exception("Incorrect username or password", e);
//        }
        final User user = userRepository.findByEmail(loginRequest.getUsername());
        if (user == null){
            throw new Exception("Cannot find user with email");
        }
        if (!jwtUtil.comparePassword(loginRequest.getPassword(), user.getPassword())){
            throw new Exception("Password not correct");
        }
        final String accessToken = jwtUtil.generateAccessToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(user);

        // Save refresh token to database
        // lafm stateless neen khong can
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return ResponseEntity.ok(new LoginDto(accessToken, refreshToken,toDto.map(user, UserProfileDto.class) ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody @Valid RefreshTokenInput refreshTokenRequest) throws Exception {
        final String refreshToken = refreshTokenRequest.getRefreshToken();

        // Check if the refresh token is valid and not expired
        final User userDetails = userRepository.findByEmail(jwtUtil.getUsernameFromToken(refreshToken));
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
            final String accessToken = jwtUtil.generateAccessToken(userDetails);
            return ResponseEntity.ok(new RefreshTokenDto(accessToken, refreshToken));
        }
        throw new Exception("Invalid refresh token");
    }

}