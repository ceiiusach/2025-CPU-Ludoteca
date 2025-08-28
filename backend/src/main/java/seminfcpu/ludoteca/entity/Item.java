package seminfcpu.ludoteca.entity;

import jakarta.persistence.*;
import lombok.*;
import seminfcpu.ludoteca.model.ItemType;

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
    private int stock;

    public void decreaseStock() {
        this.stock--;
    }

    public boolean isAvailable() {
        return stock > 0;
    }
}
