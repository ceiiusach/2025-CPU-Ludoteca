package seminfcpu.ludoteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "validationemail")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ValidationEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String code;
    private LocalDateTime expirationCode;
}
