package seminfcpu.ludoteca.entity;

import jakarta.persistence.*;
import lombok.Data;
import seminfcpu.ludoteca.model.UserRole;

import java.util.UUID;

@Data
@Entity(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UserRole role;
    @Column(unique = true)
    private String email;
}
