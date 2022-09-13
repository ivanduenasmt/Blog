package com.apiRest.blog.entidad;
import javax.persistence.*;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String cuerpo;
/*En el dto solo se genera un constructor vacio????*/
    public Comentario() {
    }

    public Comentario(Long id, String nombre, String email, String cuerpo, Publicacion publicacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.cuerpo = cuerpo;
        this.publicacion = publicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    @ManyToOne(fetch = FetchType.LAZY) /*Para que la carga se haga de manera perezosa */
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
