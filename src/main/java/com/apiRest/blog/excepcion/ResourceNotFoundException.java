package com.apiRest.blog.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Linea 9 es para que siempre que halla una excepción sea del tipo NOT_FOUND
 * Linea 15 es para establecer la version de la serializacion (Serializar es el proceso de convertir un objeto a bytes)
 * Linea 20 es para construir una excepción
 * Linea 21 es para dar formato a la respuesta de la excepción
 * */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String nombreDelRecurso;
    private String nombreDelCampo;
    private long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s No encontrada con : %s : '%s'",nombreDelRecurso, nombreDelCampo,valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }

    public String getNombreDelRecurso() {
        return nombreDelRecurso;
    }

    public void setNombreDelRecurso(String nombreDelRecurso) {
        this.nombreDelRecurso = nombreDelRecurso;
    }

    public String getNombreDelCampo() {
        return nombreDelCampo;
    }

    public void setNombreDelCampo(String nombreDelCampo) {
        this.nombreDelCampo = nombreDelCampo;
    }

    public long getValorDelCampo() {
        return valorDelCampo;
    }

    public void setValorDelCampo(long valorDelCampo) {
        this.valorDelCampo = valorDelCampo;
    }
}
