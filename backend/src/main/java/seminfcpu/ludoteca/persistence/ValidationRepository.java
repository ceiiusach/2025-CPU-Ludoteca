package seminfcpu.ludoteca.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seminfcpu.ludoteca.entity.ValidationEmail;

import java.util.UUID;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationEmail, UUID> {
    public ValidationEmail findByEmail(String email);

}
