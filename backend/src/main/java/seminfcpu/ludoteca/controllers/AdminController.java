package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.dto.ItemDto;
import seminfcpu.ludoteca.dto.LoanDto;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.entity.Loan;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.ItemService;
import seminfcpu.ludoteca.service.LoanService;
import seminfcpu.ludoteca.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public final class AdminController {
    private final UserService userService;
    private final ItemService itemService;
    private final LoanService loanService;

    public AdminController(UserService userService, @NotNull ItemService itemService, LoanService loanService) {
        this.userService = userService;
        this.itemService = itemService;
        this.loanService = loanService;
    }

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return {@link ResponseEntity} con la lista de objetos {@link User} y código <b>200 OK</b>.
     */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Busca un usuario por su identificador único (UUID).
     *
     * @param id Identificador único del usuario.
     * @return {@link ResponseEntity} con:
     * <ul>
     *     <li>El objeto {@link User} y <b>200 OK</b> si se encuentra.</li>
     *     <li><b>404 NOT FOUND</b> si no existe un usuario con ese ID.</li>
     * </ul>
     */
    @GetMapping("/user/by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.of(userService.getById(id));
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario. Se acepta el patrón completo con '@' y dominio.
     * @return {@link ResponseEntity} con:
     * <ul>
     *     <li>El objeto {@link User} y <b>200 OK</b> si se encuentra.</li>
     *     <li><b>404 NOT FOUND</b> si no existe un usuario con ese correo.</li>
     * </ul>
     */
    @GetMapping("/user/by-email/{email:.+}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param user Objeto {@link User} con la información actualizada. Debe contener el ID para poder ser identificado.
     * @return {@link ResponseEntity} con el objeto {@link User} actualizado y código <b>200 OK</b>.
     */
    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    /**
     * Elimina un usuario existente a partir de su identificador único (UUID).
     *
     * @param id Identificador único del usuario a eliminar.
     * @return {@link ResponseEntity} con código <b>200 OK</b> si la eliminación fue exitosa.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Crea un item.
     *
     * @param item Objeto {@link Item} con la información.
     * @return {@link ResponseEntity} con el objeto {@link Item} creado y código <b>200 OK</b>.
     */
    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody ItemDto item) {
        try {
            return ResponseEntity.ok(itemService.create(item));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza los datos de un item existente.
     *
     * @param item Objeto {@link Item} con la información actualizada. Debe contener el ID para poder ser identificado.
     * @return {@link ResponseEntity} con el objeto {@link Item} actualizado y código <b>200 OK</b>.
     */
    @PutMapping("/item")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.update(item));
    }

    /**
     * Elimina un item existente a partir de su ID.
     *
     * @param id Identificador único del item a eliminar.
     * @return {@link ResponseEntity} con código <b>200 OK</b> si la eliminación fue exitosa.
     */
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Boolean> deleteItem(@PathVariable UUID id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Crea un préstamo de un ítem.
     *
     * @param loan Objeto {@link LoanDto} con la información.
     * @return {@link ResponseEntity} con el objeto {@link Loan} creado y código <b>200 OK</b>.
     */
    @PostMapping("/loan")
    public ResponseEntity<Loan> createLoan(@RequestBody LoanDto loan) {
        try {
            return ResponseEntity.ok(loanService.create(loan));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene la lista completa de préstamos registrados en el sistema.
     *
     * @return {@link ResponseEntity} con la lista de objetos {@link Loan} y código <b>200 OK</b>.
     */
    @GetMapping("/loan")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAll());
    }

    /**
     * Obtiene la lista de préstamos registrados para un usuario.
     *
     * @return {@link ResponseEntity} con la lista de objetos {@link Loan} y código <b>200 OK</b>.
     */
    @GetMapping("/loan/by-user-id/{userId}")
    public ResponseEntity<List<Loan>> getAllUserLoans(@PathVariable UUID userId) {
        return ResponseEntity.ok(loanService.getByUserId(userId));
    }

    /**
     * Busca un préstamo por su ID.
     *
     * @param id Identificador único del préstamo.
     * @return {@link ResponseEntity} con:
     * <ul>
     *     <li>El objeto {@link Loan} y <b>200 OK</b> si se encuentra.</li>
     *     <li><b>404 NOT FOUND</b> si no existe un préstamo con ese ID.</li>
     * </ul>
     */
    @GetMapping("/loan/by-id/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable UUID id) {
        return ResponseEntity.of(loanService.getById(id));
    }
}
