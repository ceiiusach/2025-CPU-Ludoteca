package seminfcpu.ludoteca.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.service.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import seminfcpu.ludoteca.auth.entities.Usuario;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username);
    }

    public Usuario getUserByEmail(String email) {
        return Usuario.userToUsuario(usuarioService.getByEmail(email));
    }
}
