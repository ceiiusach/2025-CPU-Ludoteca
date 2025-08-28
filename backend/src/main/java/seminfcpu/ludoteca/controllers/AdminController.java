package seminfcpu.ludoteca.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminfcpu.ludoteca.dto.ItemDto;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.service.ItemService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public final class AdminController {
    private final ItemService itemService;

    public AdminController(@NotNull ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Crea un item.
     *
     * @param item Objeto {@link Item} con la información.
     * @return {@link ResponseEntity} con el objeto {@link Item} creado y código <b>200 OK</b>.
     */
    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody ItemDto item) {
        try {
            return ResponseEntity.ok(itemService.create(item));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza los datos de un item existente.
     *
     * @param item Objeto {@link Item} con la información actualizada. Debe contener el ID para poder ser identificado.
     * @return {@link ResponseEntity} con el objeto {@link Item} actualizado y código <b>200 OK</b>.
     */
    @PutMapping("/item")
    public ResponseEntity<Item> update(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.update(item));
    }

    /**
     * Elimina un item existente a partir de su ID.
     *
     * @param id Identificador único del item a eliminar.
     * @return {@link ResponseEntity} con código <b>200 OK</b> si la eliminación fue exitosa.
     */
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }
}
