package seminfcpu.ludoteca.service;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.persistence.UserRespository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public final class UserService implements UserDetailsService {
    private final UserRespository repository;

    public UserService(@Nonnull UserRespository repository) {
        this.repository = repository;
    }

    public void create(@Nonnull User user) {
        String encodedPassword = generateEncodedPassword(user.getPassword());
        user.setPassword(encodedPassword);
        validateUsuario(user);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el usuario", e);
        }
    }

    private String generateEncodedPassword(String passsword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(passsword);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(@Nonnull UUID id) {
        return repository.findById(id);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(@Nonnull User user) {
        return repository.save(user);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private void validateUsuario(User user) {
        UUID id = user.getId();
        String correo = user.getEmail();

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }

        if (id != null && !existsUser(id)) {
            throw new IllegalArgumentException("No existe el voluntario");
        }

        if (existsUserByCorreo(correo)) {
            throw new IllegalArgumentException("El correo ingresado ya esta registrado");
        }

        if (!isValidCorreo(correo)) {
            throw new IllegalArgumentException("El correo ingresado no es valido");
        }
    }

    public boolean existsUser(UUID idVoluntario) {
        return repository.existsUserById(idVoluntario);
    }

    public boolean existsUserByCorreo(String correo) {
        return repository.existsUserByEmail(correo);
    }

    private boolean isValidCorreo(String correo) {
        System.out.println("Validando correo: " + correo);
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        boolean isValid = matcher.matches();
        System.out.println("Correo válido: " + isValid);
        return isValid;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username);
    }
}
