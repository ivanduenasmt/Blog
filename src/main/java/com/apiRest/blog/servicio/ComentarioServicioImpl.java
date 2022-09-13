package com.apiRest.blog.servicio;

import com.apiRest.blog.dto.ComentarioDto;
import com.apiRest.blog.entidad.Comentario;
import com.apiRest.blog.entidad.Publicacion;
import com.apiRest.blog.excepcion.BlogAppException;
import com.apiRest.blog.excepcion.ResourceNotFoundException;
import com.apiRest.blog.repositorio.ComentarioRepositorio;
import com.apiRest.blog.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImpl implements ComentarioServicio{

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * !: El comentario recibido como parámetro lo mapeo a entidad
     * !!: Busco la publicacion a la que asigno el comentario
     * !!!: Desde comentario establezco la publicacion a la que corresponde
     * !!!!: Guardo el comentario
     * !!!!!: Retorno |*/
    @Override
    public ComentarioDto crearComentario(long publicacionId, ComentarioDto comentarioDto) {
        Comentario comentario = mapearEntidad(comentarioDto);
        Publicacion publicacion = this.publicacionRepositorio.findById(publicacionId).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = this.comentarioRepositorio.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDto> listarComentariosPorPublicacionId(long publicacionId) {
        List<Comentario> comentarios = this.comentarioRepositorio.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDto obtenerComentarioPorId(long publicacionId, long comentarioId) {
        Publicacion publicacion = this.publicacionRepositorio.findById(publicacionId).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = this.comentarioRepositorio.findById(comentarioId).
                orElseThrow(() -> new ResourceNotFoundException("Comentario","id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación.");
        }
        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDto actualizarComentario(long publicacionId, ComentarioDto solicitudDeComentario, Long comentarioId) {
        Publicacion publicacion = this.publicacionRepositorio.findById(publicacionId).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = this.comentarioRepositorio.findById(comentarioId).
                orElseThrow(() -> new ResourceNotFoundException("Comentario","id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación.");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = this.comentarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(long publicacionId, long comentarioId) {
        Publicacion publicacion = this.publicacionRepositorio.findById(publicacionId).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = this.comentarioRepositorio.findById(comentarioId).
                orElseThrow(() -> new ResourceNotFoundException("Comentario","id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación.");
        }
        this.comentarioRepositorio.deleteById(comentarioId);
    }


    private ComentarioDto mapearDTO(Comentario comentario){
        ComentarioDto comentarioDto = modelMapper.map(comentario, ComentarioDto.class);
        return comentarioDto;
    }

    private Comentario mapearEntidad(ComentarioDto comentarioDto){
        Comentario comentario = modelMapper.map(comentarioDto, Comentario.class);
        return comentario;
    }
}
