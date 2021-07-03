package nl.hu.cisq1.lingo.trainer.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import nl.hu.cisq1.lingo.trainer.application.exception.NotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ExceptionResponse;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ IllegalArgumentException.class, GuessException.class })
    public ResponseEntity<ExceptionResponse> handleConflict(Exception e) {
        return new ResponseEntity<>(
            new ExceptionResponse(e.getLocalizedMessage()),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ExceptionResponse> handleConflict(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
            new ExceptionResponse(e.getValue() + " is not a proper " + e.getRequiredType().getSimpleName()),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String[] message = e.getRootCause().getLocalizedMessage().split("Detail: ");
        return new ResponseEntity<>(
            new ExceptionResponse(message[message.length - 1]),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ExceptionResponse> handleNotFound(Exception e) {
        return new ResponseEntity<>(
            new ExceptionResponse(e.getLocalizedMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<ExceptionResponse> handleHttpNotReadable(Exception e) {
        return new ResponseEntity<>(
            new ExceptionResponse("Body is missing!"),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(
            new ExceptionResponse(e.getLocalizedMessage()),
            HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    // Taken from https://www.baeldung.com/spring-boot-bean-validation#the-exceptionhandler-annotation
    // Handles all Method @Validated request exceptions
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
