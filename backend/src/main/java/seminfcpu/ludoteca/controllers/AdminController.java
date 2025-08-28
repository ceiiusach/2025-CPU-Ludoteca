package seminfcpu.ludoteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminfcpu.ludoteca.dto.Item;
import seminfcpu.ludoteca.service.ItemService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ItemService itemService;

    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("")
    public ResponseEntity<String> crearItem(@RequestBody Item item) {
        try {
            itemService.crearItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item creado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el item");
        }
    }

}
