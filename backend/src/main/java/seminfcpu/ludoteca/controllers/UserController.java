package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public final class UserController {

    private final UserService service;

    public UserController(@NotNull UserService service) {
        this.service = service;
    }

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return {@link ResponseEntity} con la lista de objetos {@link User} y código <b>200 OK</b>.
     */
    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Busca un usuario por su identificador único (UUID).
     *
     * @param id Identificador único del usuario.
     * @return {@link ResponseEntity} con:
     *         <ul>
     *             <li>El objeto {@link User} y <b>200 OK</b> si se encuentra.</li>
     *             <li><b>404 NOT FOUND</b> si no existe un usuario con ese ID.</li>
     *         </ul>
     */
    @GetMapping("/by-id/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return ResponseEntity.of(service.getById(id));
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario. Se acepta el patrón completo con '@' y dominio.
     * @return {@link ResponseEntity} con:
     *         <ul>
     *             <li>El objeto {@link User} y <b>200 OK</b> si se encuentra.</li>
     *             <li><b>404 NOT FOUND</b> si no existe un usuario con ese correo.</li>
     *         </ul>
     */
    @GetMapping("/by-email/{email:.+}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = service.getByEmail(email);
        return (user != null) ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param user Objeto {@link User} con la información actualizada. Debe contener el ID para poder ser identificado.
     * @return {@link ResponseEntity} con el objeto {@link User} actualizado y código <b>200 OK</b>.
     */
    @PutMapping("")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(service.update(user));
    }

    /**
     * Elimina un usuario existente a partir de su identificador único (UUID).
     *
     * @param id Identificador único del usuario a eliminar.
     * @return {@link ResponseEntity} con código <b>200 OK</b> si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
