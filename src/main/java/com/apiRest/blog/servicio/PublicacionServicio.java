package com.apiRest.blog.servicio;

import com.apiRest.blog.dto.PublicacionDto;
import com.apiRest.blog.dto.PublicacionRespuesta;

import java.util.List;

public interface PublicacionServicio {

    PublicacionDto crearPublicacion(PublicacionDto publicacionDto);

    PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    PublicacionDto obtenerPublicacionPorId(long id);

    PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto,long id);

    void eliminarPublicacion(long id);
}
