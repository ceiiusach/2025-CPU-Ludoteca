package seminfcpu.ludoteca.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import seminfcpu.ludoteca.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsUserById(UUID id);
    boolean existsUserByEmail(String email);
}
