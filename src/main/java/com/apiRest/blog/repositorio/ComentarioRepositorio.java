package com.apiRest.blog.repositorio;

import com.apiRest.blog.entidad.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacionId(long publicacionId);
}
