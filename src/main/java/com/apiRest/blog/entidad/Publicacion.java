package com.apiRest.blog.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * No puede haber una publicacion con titulo repetido, para ello el uniqueConstraint
 * */
@Entity
@Table(name = "publicaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "contenido", nullable = false)
    private String contenido;

    /*Cuando elimine un valor se elimine todos los datos asociados, el cascade, para cuando elimine objeto publicacion se valla los comentarios */
    @JsonBackReference /*Eliminar recursión infnita */
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true) /*orphan removal la instancia de direccion se elimina automaticamente, util para eliminr la publicacion con los comentarios */
    private Set<Comentario> comentarios = new HashSet<>();

    public Publicacion() {
    }

    public Publicacion(Long id, String titulo, String descripcion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
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