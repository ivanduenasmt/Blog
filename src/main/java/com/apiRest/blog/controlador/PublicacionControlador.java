package com.apiRest.blog.controlador;

import com.apiRest.blog.dto.PublicacionDto;
import com.apiRest.blog.dto.PublicacionRespuesta;
import com.apiRest.blog.servicio.PublicacionServicio;
import com.apiRest.blog.util.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/publicaciones")
public class PublicacionControlador {

    @Autowired
    private PublicacionServicio publicacionServicio;

    @PostMapping
    public ResponseEntity<PublicacionDto> guardarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto){
        return new ResponseEntity<>(this.publicacionServicio.crearPublicacion(publicacionDto), HttpStatus.CREATED);
    }

    /**
     * El requestParam es usado para efectuar la paginación, valor por defecto es cero y no es requerido , tipo de dato
     * y número de página, establezco dos datos para paginar, numero de página y medida de página */
    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA, required = false) int numeroDePagina,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA, required = false) int medidaDePágina,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir){
        return this.publicacionServicio.obtenerTodasLasPublicaciones(numeroDePagina, medidaDePágina,ordenarPor,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDto> buscarPorId(@PathVariable("id") long id){
        return new ResponseEntity<>(this.publicacionServicio.obtenerPublicacionPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDto> actualizarPublicacion (@Valid @PathVariable("id") long id,@RequestBody PublicacionDto publicacionDto) {
        return  new ResponseEntity<>(this.publicacionServicio.actualizarPublicacion(publicacionDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable("id") long id){
        this.publicacionServicio.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicación eliminada con exito",HttpStatus.OK);
    }
}

