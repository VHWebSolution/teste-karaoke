package com.vhws.karaoke.handler;

import com.vhws.karaoke.entity.ecxeption.ForbiddenException;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.ecxeption.ResourceInternalServerException;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Este método faz o tratamento do erro 404 Not Found
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Este método faz o tratamento do erro 400 Bad Request
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Bad Request
     */
    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<?> handleResourceBadRequestException(ResourceBadRequestException ex){
        ErrorMessage error = new ErrorMessage("Bad Request", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Este método faz o tratamento do erro 500 Internal Server Error
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Internal Server Error
     */
    @ExceptionHandler(ResourceInternalServerException.class)
    public ResponseEntity<?> handleResourceInternalServerException(ResourceInternalServerException ex){
        ErrorMessage error = new ErrorMessage("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException ex){
        ErrorMessage error = new ErrorMessage("Forbidden", HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}