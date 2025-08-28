package seminfcpu.ludoteca.auth.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.auth.dto.AuthResponse;
import seminfcpu.ludoteca.auth.dto.LoginRequest;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.UserService;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthService(@NotNull JwtService jwtService, @NotNull AuthenticationManager authenticationManager, @NotNull UserService userService){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService.getByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }
}
