package com.apiRest.blog.repositorio;

import com.apiRest.blog.entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}
