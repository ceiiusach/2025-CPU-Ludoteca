package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public final class UserController {
    private final UserService service;

    public UserController(@NotNull UserService service) {
        this.service = service;
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> listStudent() {
        List<User> students = service.listStudents();
        return ResponseEntity.ok(students);
    }
}
