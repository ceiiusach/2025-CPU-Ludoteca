package seminfcpu.ludoteca.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.model.UserRole;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsUserById(UUID id);
    boolean existsUserByEmail(String email);

    List<User> findByRole(UserRole role);
}
