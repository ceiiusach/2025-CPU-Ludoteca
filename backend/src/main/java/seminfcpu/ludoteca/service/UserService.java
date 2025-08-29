package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.dto.UserDto;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.model.UserRole;
import seminfcpu.ludoteca.persistence.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public final class UserService implements UserDetailsService {
    private final UserRepository repository;

    public UserService(@NotNull UserRepository repository) {
        this.repository = repository;
    }

    public User create(@NotNull User user) {
        validate(user);
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public List<User> listStudents(){
        return repository.findByRole(UserRole.STUDENT);
    }

    public Optional<User> getById(@NotNull UUID id) {
        return repository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(@NotNull User user) {
        return repository.save(user);
    }

    public User update(@NotNull UserDto userDto) {
        User user = getById(userDto.getId()).orElseThrow();
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        return repository.save(user);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private void validate(@NotNull User user) {
        UUID id = user.getId();
        String correo = user.getEmail();

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }

        if (id != null && !exists(id)) {
            throw new IllegalArgumentException("No existe el voluntario");
        }

        if (existsByEmail(correo)) {
            throw new IllegalArgumentException("El correo ingresado ya esta registrado");
        }

        if (!isEmailValid(correo)) {
            throw new IllegalArgumentException("El correo ingresado no es valido");
        }
    }

    public boolean exists(UUID id) {
        return repository.existsUserById(id);
    }

    public boolean existsByEmail(String email) {
        return repository.existsUserByEmail(email);
    }

    private boolean isEmailValid(String email) {
        if (!email.endsWith("@usach.cl")) return false;

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username).orElseThrow();
    }
}
