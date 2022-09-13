package com.apiRest.blog.servicio;

import com.apiRest.blog.dto.PublicacionDto;
import com.apiRest.blog.dto.PublicacionRespuesta;
import com.apiRest.blog.entidad.Publicacion;
import com.apiRest.blog.excepcion.ResourceNotFoundException;
import com.apiRest.blog.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {


    /**
     * En caso de realizar pruebas unitarias no usar autowired, instanciar la clase mediante un constructor que utilice
     * la publicacionRepositorio como parametro y la inyecte */
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    /*Inyecto el mapper que mapea de dto a entidad y la inversa */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * Creacion = Recibe un Dto (Json) de publicacion, primero este JSON lo convierto en entidad para poderlo guardar
     * en un objeto (persistido en db). Despues de guardado retorno una respuesta en formate JSON.
     */
    @Override
    public PublicacionDto crearPublicacion(PublicacionDto publicacionDto) {
        Publicacion publicacionNueva = this.publicacionRepositorio.save(mapearEntidad(publicacionDto));
        return mapearDTO(publicacionNueva);
    }


    /**
     * Obtener una lista de las publicaciones de la db y convertirlas en formato JSON para ser devueltas como una lista
     * de datos */
    /**
     * Los paramétros seran los usados en la paginación, instancio un Pageable, alli mediante el RequestOf meto los para
     * metros, para despues instanciar un Page de publicacion y alli buscar y suministrar el pageable.Finalmente obtengo
     * la lista de las publicaciones del page.getcontent
     */
    /**
     * Linea 57 = La lista se establece como contenido a su vez dentro de la publicación respuesta */
    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,String sortDir) {
        /*Voy a pasar un sort para ordenar, si este sortDir es = a direccion asc y con un nombre, voy a ordenar ascendente
        * Sino, lo voy a ordenar de forma descendente*/
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina, sort);

        Page<Publicacion> publicaciones = this.publicacionRepositorio.findAll(pageable);
        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDto> contenido =  listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroDePagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaDePagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }


    /*
    * Busca la publicacion y en caso de no encontrarla (orElseThrow) lanza una excepción , finalmente el objeto encontr
    * ado lo mapea a dto para devolverlo acorde al método */
    @Override
    public PublicacionDto obtenerPublicacionPorId(long id) {
        Publicacion publicacion = this.publicacionRepositorio.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion","id", id));
        return mapearDTO(publicacion);
    }


    /**
     * Buscamos la publicacion, si la encuentra establecemos valores y guardamos y retornamos la publicacion */
    @Override
    public PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto, long id) {
        Publicacion publicacion = this.publicacionRepositorio.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion","id", id));

        publicacion.setTitulo(publicacionDto.getTitulo());
        publicacion.setDescripcion(publicacionDto.getDescripcion());
        publicacion.setContenido(publicacionDto.getContenido());

        Publicacion publicacionActualizada = this.publicacionRepositorio.save(publicacion);
        return mapearDTO(publicacionActualizada);
    }


    /**
     * Se precisa buscar la publicacion y verificar su existencia, si existe se procede a borrarla, si no , se lanza una
     * nueva excepción */
    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = this.publicacionRepositorio.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Publicacion","id", id));
        this.publicacionRepositorio.delete(publicacion);
    }


    /*
    * Convierte la entidad a dto */
    private PublicacionDto mapearDTO(Publicacion publicacion){
        PublicacionDto publicacionDto = modelMapper.map(publicacion, PublicacionDto.class);
        return publicacionDto;
    }


    /**
     * Convierte el dto a entidad */
    private Publicacion mapearEntidad(PublicacionDto publicacionDto){
        Publicacion publicacion = modelMapper.map(publicacionDto, Publicacion.class);
        return publicacion;
    }
}
