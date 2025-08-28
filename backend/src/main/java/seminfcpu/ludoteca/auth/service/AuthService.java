package seminfcpu.ludoteca.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.auth.entities.AuthResponse;
import seminfcpu.ludoteca.auth.entities.LoginRequest;
import seminfcpu.ludoteca.auth.entities.Usuario;
import seminfcpu.ludoteca.entity.User;

@Service
public class AuthService {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario user = userService.getUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }
}
