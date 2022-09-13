package com.apiRest.blog.dto;

import com.apiRest.blog.entidad.Comentario;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class PublicacionDto {

    /*USO DE VALIDATION Linea 15,17,23*/
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicación debe tener mas de dos carácteres")
    private String titulo;

    @NotEmpty
    @Size(min = 10, message = "La descripción de la publicación debe tener mas de diez carácteres")
    private String descripcion;

    @NotEmpty

    private String contenido;
    /*Atributo creado para que me muestre los comentarios una vez le doy obtener todooooo con getter y setter,
    pero ojo se produce errore infinito, suele suceder couando se trabaja con dto, para solucionar,agregar anotacion Json
    Back reference en la entidad publicacion sobre la relacion oneToMany
    * */
    private Set<Comentario> comentarios = new HashSet<>();

    public PublicacionDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
