package com.apiRest.blog.seguridad;

import com.apiRest.blog.entidad.Rol;
import com.apiRest.blog.entidad.Usuario;
import com.apiRest.blog.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*Esta clase es creada para ser usada como servicio de la implementación de la entidad usuario, aqui es inyectado el
* usario repositorio y utilizados los metodos del mismo, para cargar usuario por nombre de usuario y para mapear los roles
*  */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepositorio.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).
                orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese Username o Email "+ usernameOrEmail));

        return new User(usuario.getEmail(),usuario.getPassword(), mapearRoles(usuario.getRoles()));
    }

    // Granted autority lya que son las autirdades o los roles
    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }
}
