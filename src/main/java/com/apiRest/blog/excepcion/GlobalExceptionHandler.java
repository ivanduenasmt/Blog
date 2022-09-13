package com.apiRest.blog.excepcion;

import com.apiRest.blog.dto.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*Esta notación le va a decir a esta clase que va a poder manejar excepciones, no solo de un controladr sino todas las capturas de excepcione del controlador */
@ControllerAdvice
/*se pone el extends porque voy a añadir un metodo para indicarle cuando no esta valido */
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*Esta anotacion va aservir para menajr las excepciones que se hallan detallado a la propia excepcion*/
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    * Metodo para indicarle cuando no está valido
    * 1. Cuando lo que estoy enviando no es valido me va a dar una handl como manejar una excepcion tambien
    * 2.Por ejemplo: Si me voy a publicacion Dto y voy a enviar un titulo vacio este metodo me va a controlar esos errores
    * 3. Creo un map para controlar los errores, en modo hashmap
    * 4.El obtiene todo lo errores, y vamos a recorrerlos un o por uno una vez reccoriendo obtengo nombredel campo del error,
                   lo cmapeo a fielderror y obtener campo y errores. put para asignar valores al hashmap
        */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();

            errores.put(nombreCampo,mensaje);
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
