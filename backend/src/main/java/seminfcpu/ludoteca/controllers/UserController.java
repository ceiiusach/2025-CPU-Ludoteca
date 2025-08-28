package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public final class UserController {
    private final UserService service;

    public UserController(@NotNull UserService service) {
        this.service = service;
    }

    //TODO Permitir actualizar la propia contraseña?
}
