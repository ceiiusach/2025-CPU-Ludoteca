package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminfcpu.ludoteca.service.LoanService;
import seminfcpu.ludoteca.service.UserService;

@RestController
@RequestMapping("/api/v1/loan")
public final class LoanController {
    private final LoanService service;

    public LoanController(@NotNull LoanService service) {
        this.service = service;
    }

    //TODO Permitir ver los propios préstamos
}
