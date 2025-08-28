package seminfcpu.ludoteca.dto;

import lombok.Getter;
import lombok.Setter;
import seminfcpu.ludoteca.model.ItemType;

@Getter
@Setter
public class Item {
    private String name;
    private String description;
    private ItemType type;
    private int stock;
}