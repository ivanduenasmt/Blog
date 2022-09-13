package com.apiRest.blog.controlador;

import com.apiRest.blog.dto.ComentarioDto;
import com.apiRest.blog.servicio.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDto> crearComentario(@PathVariable("publicacionId") Long publicacionId,@Valid @RequestBody ComentarioDto comentarioDto){
        ComentarioDto nuevoComentario = this.comentarioServicio.crearComentario(publicacionId, comentarioDto);
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDto> listarComentariosPorPublicacionId(@PathVariable("publicacionId") Long publicacionId){
        List<ComentarioDto> comentarioDtoList = this.comentarioServicio.listarComentariosPorPublicacionId(publicacionId);
        return comentarioDtoList;
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDto> obtenerComentarioPorId(@PathVariable("publicacionId") Long publicacionId,@PathVariable("comentarioId") Long comentarioId){
        ComentarioDto comentarioDto = this.comentarioServicio.obtenerComentarioPorId(comentarioId, publicacionId);
        return new ResponseEntity<>(comentarioDto, HttpStatus.OK);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDto> actualizarComentarioPorId(@PathVariable("publicacionId") Long publicacionId,@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable("comentarioId") Long comentarioId){
        ComentarioDto comentarioActualizado = this.comentarioServicio.actualizarComentario(publicacionId,comentarioDto,comentarioId);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable("publicacionId") Long publicacionId, @PathVariable("comentarioId") Long comentarioId){
        this.comentarioServicio.eliminarComentario(publicacionId,comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }
}
