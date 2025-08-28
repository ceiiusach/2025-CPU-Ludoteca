package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.service.ItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item")
public final class ItemController {
    private final ItemService service;

    public ItemController(@NotNull ItemService service) {
        this.service = service;
    }

    /**
     * Obtiene la lista completa de items registrados en el sistema.
     *
     * @return {@link ResponseEntity} con la lista de objetos {@link Item} y código <b>200 OK</b>.
     */
    @GetMapping("")
    public ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Busca un item por su ID.
     *
     * @param id Identificador único del item.
     * @return {@link ResponseEntity} con:
     *         <ul>
     *             <li>El objeto {@link Item} y <b>200 OK</b> si se encuentra.</li>
     *             <li><b>404 NOT FOUND</b> si no existe un usuario con ese ID.</li>
     *         </ul>
     */
    @GetMapping("/by-id/{id}")
    public ResponseEntity<Item> getById(@PathVariable UUID id) {
        return ResponseEntity.of(service.getById(id));
    }
}
