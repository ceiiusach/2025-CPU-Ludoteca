package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.dto.ItemDto;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.persistence.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item create(ItemDto itemDto) {
        Item newItem = new Item();
        newItem.setName(itemDto.getName());
        newItem.setDescription(itemDto.getDescription());
        newItem.setType(itemDto.getType());
        newItem.setStock(itemDto.getStock());
        return repository.save(newItem);
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    public Optional<Item> getById(@NotNull UUID id) {
        return repository.findById(id);
    }

    public Item update(@NotNull Item item) {
        return repository.save(item);
    }

    public void delete(@NotNull UUID id) {
        repository.deleteById(id);
    }
}
