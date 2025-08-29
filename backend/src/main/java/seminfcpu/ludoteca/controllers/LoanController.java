package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminfcpu.ludoteca.dto.LoanDto;
import seminfcpu.ludoteca.entity.Loan;
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

    /**
     * Crea un préstamo de un ítem.
     *
     * @param loan Objeto {@link LoanDto} con la información.
     * @return {@link ResponseEntity} con el objeto {@link Loan} creado y código <b>200 OK</b>.
     */
    @PostMapping("")
    public ResponseEntity<Loan> createLoan(@RequestBody LoanDto loan) {
        try {
            return ResponseEntity.ok(service.create(loan));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
