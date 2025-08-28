package seminfcpu.ludoteca.service;

import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.dto.Item;
import seminfcpu.ludoteca.persistence.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void crearItem(Item item) {
        seminfcpu.ludoteca.entity.Item newItem = new seminfcpu.ludoteca.entity.Item();
        newItem.setName(item.getName());
        newItem.setDescription(item.getDescription());
        newItem.setType(item.getType());
        newItem.setStock(item.getStock());
        if (newItem.getStock() > 0) {
            newItem.setAvailable(true);
        } else {
            newItem.setAvailable(false);
        }
        itemRepository.save(newItem);
    }
}
