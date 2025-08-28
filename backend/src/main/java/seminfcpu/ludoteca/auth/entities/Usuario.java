package seminfcpu.ludoteca.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import seminfcpu.ludoteca.entity.User;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private static String ROLE = "ROLE_USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public static Usuario userToUsuario(User user) {
        return new Usuario(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public Map<String, Object> generateExtraClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("name", name);
        return claims;
    }
}
