package seminfcpu.ludoteca.entity;

import jakarta.persistence.*;
import lombok.*;
import seminfcpu.ludoteca.model.ItemType;
import seminfcpu.ludoteca.model.UserRole;

import java.util.UUID;

@Data
@Entity(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private ItemType type;
    private boolean available;
    private int stock;
}
