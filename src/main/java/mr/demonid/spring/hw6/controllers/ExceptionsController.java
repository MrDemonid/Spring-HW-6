package mr.demonid.spring.hw6.controllers;

import mr.demonid.spring.hw6.exceptions.NoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Глобальный обработчик ошибок.
 */
@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(NoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String noteException(NoteException e) {
        return  LocalDateTime.now().toString() + ": " + e.getMessage();
    }
//    public Map<String, String> noteException(NoteException e) {
//        return Map.of(
//                "timestamp", LocalDateTime.now().toString(),
//                "error", e.getMessage()
//        );
//    }


}
