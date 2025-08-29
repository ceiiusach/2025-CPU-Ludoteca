package seminfcpu.ludoteca.auth.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.auth.dto.AuthResponse;
import seminfcpu.ludoteca.auth.dto.LoginRequest;
import seminfcpu.ludoteca.auth.service.AuthService;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.EmailService;
import seminfcpu.ludoteca.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public final class AuthenticationController {
    private final AuthService service;
    private final EmailService emailService;
    private final UserService userService;

    public AuthenticationController(@NotNull AuthService service, EmailService emailService, UserService userService) {
        this.service = service;
        this.emailService = emailService;
        this.userService = userService;
    }

    /**
     * Autentica a un usuario en el sistema.
     *
     * <p>Este endpoint valida las credenciales enviadas (correo electrónico y contraseña)
     * a través del objeto {@link LoginRequest}. Si las credenciales son correctas,
     * se genera un token JWT que permitirá al usuario acceder a los recursos protegidos.</p>
     *
     * @param request Objeto {@link LoginRequest} con los campos necesarios para autenticación
     *                (email y contraseña).
     * @return {@link ResponseEntity} que contiene un objeto {@link AuthResponse} con:
     * <ul>
     *     <li><b>200 OK</b> junto con el token de acceso y, opcionalmente, token de refresco.</li>
     *     <li><b>401 UNAUTHORIZED</b> si las credenciales son inválidas.</li>
     * </ul>
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Objeto {@link LoginRequest} con los campos necesarios para autenticación
     *                (email y contraseña).
     * @return {@link ResponseEntity} con:
     * <ul>
     *     <li><b>201 CREATED</b> y mensaje de éxito si el usuario fue creado correctamente.</li>
     *     <li><b>400 BAD REQUEST</b> si ocurre algún error durante el proceso de creación.</li>
     * </ul>
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestCode(@RequestParam String email) {
        boolean sent = emailService.sendVerificationCode(email);
        if (sent) {
            return ResponseEntity.ok("Código enviado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo enviar el código");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateCode(@RequestParam String email, @RequestParam String code) {
        User user = userService.getByEmail(email).orElseThrow();
        if (user.getExpirationCode().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        boolean valid = emailService.verifyCode(email, code);
        if (valid) {
            return ResponseEntity.ok("Código válido.");
        } else {
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        }
    }
}
