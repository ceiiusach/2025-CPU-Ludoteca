package seminfcpu.ludoteca.auth.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.auth.config.AppAuthConfig;
import seminfcpu.ludoteca.auth.dto.AuthResponse;
import seminfcpu.ludoteca.auth.dto.LoginRequest;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.model.UserRole;
import seminfcpu.ludoteca.service.UserService;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppAuthConfig appAuthConfig;
    private final UserService userService;

    public AuthService(@NotNull JwtService jwtService, @NotNull AuthenticationManager authenticationManager, AppAuthConfig appAuthConfig, @NotNull UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.appAuthConfig = appAuthConfig;
        this.userService = userService;
    }

    public AuthResponse register(@NotNull LoginRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(appAuthConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(UserRole.STUDENT);
        userService.create(user);

        String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
        return AuthResponse.builder().token(jwtToken).userId(user.getId()).build();
    }

    public AuthResponse authenticate(@NotNull LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userService.getByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
        return AuthResponse.builder().token(jwtToken).userId(user.getId()).build();
    }
}
