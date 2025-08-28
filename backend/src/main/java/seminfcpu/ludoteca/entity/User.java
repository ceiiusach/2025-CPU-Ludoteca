package seminfcpu.ludoteca.entity;

import jakarta.persistence.*;
import lombok.*;
import seminfcpu.ludoteca.model.UserRole;

import java.util.UUID;

@Data
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UserRole role;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
}
