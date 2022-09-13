package com.apiRest.blog.dto;

import java.util.List;

public class PublicacionRespuesta {

    private List<PublicacionDto> contenido;
    private Integer numeroDePagina;
    private Integer medidaDePagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public PublicacionRespuesta() {
    }

    public List<PublicacionDto> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionDto> contenido) {
        this.contenido = contenido;
    }

    public Integer getNumeroDePagina() {
        return numeroDePagina;
    }

    public void setNumeroDePagina(Integer numeroDePagina) {
        this.numeroDePagina = numeroDePagina;
    }

    public Integer getMedidaDePagina() {
        return medidaDePagina;
    }

    public void setMedidaDePagina(Integer medidaDePagina) {
        this.medidaDePagina = medidaDePagina;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}
