package seminfcpu.ludoteca.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminfcpu.ludoteca.auth.entities.AuthResponse;
import seminfcpu.ludoteca.auth.entities.LoginRequest;
import seminfcpu.ludoteca.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthService authenticationService;

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
     *         <ul>
     *             <li><b>200 OK</b> junto con el token de acceso y, opcionalmente, token de refresco.</li>
     *             <li><b>401 UNAUTHORIZED</b> si las credenciales son inválidas.</li>
     *         </ul>
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
