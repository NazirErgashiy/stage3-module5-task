package com.mjc.school.service.error;

import com.mjc.school.service.error.dto.ExceptionResponse;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 404
     */
    @ExceptionHandler(NotFoundRuntimeException.class)
    public ResponseEntity handleResourceNotFoundException(NotFoundRuntimeException ex) {
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * 400
     * Override MethodArgumentNotValidException
     * Do not annotate with @ExceptionHandler(MethodArgumentNotValidException.class)
     * It causes “Ambiguous @ExceptionHandler method mapped“ error
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.name(), LocalDateTime.now()));
    }

    /**
     * 409.
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity handleIllegalExceptions(RuntimeException ex) {
        return buildResponseEntity(ex, HttpStatus.CONFLICT);
    }

    /**
     * 500.
     * Throw exception for any other unpredicted reason.
     * Avoid modification of this method to make problem visible in logs.
     * Don't delete this method to avoid descendants declare it and catch 'any error'.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleOtherExceptions(Exception ex) throws Exception {
        throw ex;
    }

    private ResponseEntity<ExceptionResponse> buildResponseEntity(Exception ex, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ExceptionResponse(ex.getMessage(), httpStatus.name(), LocalDateTime.now()));
    }
}
