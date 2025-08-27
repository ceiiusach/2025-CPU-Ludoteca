package seminfcpu.ludoteca.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.UserService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public final class UserController {
    private final UserService service;

    public UserController(@Nonnull UserService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<User> create(@RequestBody User client) {
        User result = service.create(client);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return ResponseEntity.of(service.getById(id));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        return ResponseEntity.of(service.getByEmail(email));
    }

    @PutMapping("")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(service.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
