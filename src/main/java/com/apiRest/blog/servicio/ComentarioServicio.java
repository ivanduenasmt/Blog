package com.apiRest.blog.servicio;

import com.apiRest.blog.dto.ComentarioDto;

import java.util.List;

public interface ComentarioServicio {

    ComentarioDto crearComentario(long publicacionId, ComentarioDto comentarioDto);

    List<ComentarioDto> listarComentariosPorPublicacionId(long publicacionId);

    ComentarioDto obtenerComentarioPorId(long publicacionId, long comentarioId);

    ComentarioDto actualizarComentario(long publicacionId, ComentarioDto comentarioDto, Long comentarioId);

    void eliminarComentario(long publicacionId, long comentarioId);


}
