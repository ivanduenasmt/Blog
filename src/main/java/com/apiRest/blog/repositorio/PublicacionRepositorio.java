package com.apiRest.blog.repositorio;

import com.apiRest.blog.entidad.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
}
