package seminfcpu.ludoteca.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.entity.User;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
}
