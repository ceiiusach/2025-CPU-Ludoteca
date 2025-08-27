package seminfcpu.ludoteca.service;

import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.persistence.UserRespository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class UserService {
    private final UserRespository respository;

    public UserService(@Nonnull UserRespository respository) {
        this.respository = respository;
    }

    public User create(@Nonnull User user) {
        return respository.save(user);
    }

    public List<User> getAll() {
        return respository.findAll();
    }

    public Optional<User> getById(@Nonnull UUID id) {
        return respository.findById(id);
    }

    public Optional<User> getByEmail(@Nonnull String email) {
        return respository.findByEmail(email);
    }

    public User update(@Nonnull User user) {
        return respository.save(user);
    }

    public void delete(UUID id) {
        respository.deleteById(id);
    }
}
