package seminfcpu.ludoteca.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.model.UserRole;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private UserRole role;
    private String email;

    public UserDto(@NotNull User user) {
        id = user.getId();
        role = user.getRole();
        email = user.getEmail();
    }

    public static Optional<UserDto> optional(@Nullable User user) {
        return Optional.ofNullable(user).map(UserDto::new);
    }
}