package io.rachidassouani.booksocialnetworkapi.exception;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static io.rachidassouani.booksocialnetworkapi.exception.BusinessErrorCode.*;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleLockedException(
            LockedException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                ACCOUNT_LOCKED.getCode(),
                ACCOUNT_LOCKED.getDescription(),
                ex.getMessage(),
                request.getRequestURI(),
                null,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.LOCKED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleDisableException(
            DisabledException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                ACCOUNT_DISABLED.getCode(),
                ACCOUNT_DISABLED.getDescription(),
                ex.getMessage(),
                request.getRequestURI(),
                null,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                BAD_CREDENTIALS.getCode(),
                BAD_CREDENTIALS.getDescription(),
                ex.getMessage(),
                request.getRequestURI(),
                null,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleMessagingException(
            MessagingException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                null,
                null,
                ex.getMessage(),
                request.getRequestURI(),
                null,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    var err = error.getDefaultMessage();
                    errors.add(err);
                });
        ExceptionResponse response = new ExceptionResponse(
                BAD_CREDENTIALS.getCode(),
                BAD_CREDENTIALS.getDescription(),
                ex.getMessage(),
                request.getRequestURI(),
                errors,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(
            Exception ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                EXCEPTION.getCode(),
                EXCEPTION.getDescription(),
                ex.getMessage(),
                request.getRequestURI(),
                null,
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
