package com.olxseller.olx.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    // Override the existing method to handle validation exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle constraint violation exceptions
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String fieldName = cv.getPropertyPath().toString();
            String errorMessage = cv.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
    // Handle data integrity violations (e.g., duplicate entries)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getMostSpecificCause().getMessage().contains("Duplicate entry")) {
            return new ResponseEntity<>("Duplicate entry found. Please use a unique value.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Data integrity violation occurred. Please check your input.", HttpStatus.BAD_REQUEST);
    }

    // Handle JPA transaction exceptions
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException ex) {
        if (ex.getMostSpecificCause() instanceof RollbackException) {
            Throwable cause = ((RollbackException) ex.getMostSpecificCause()).getCause();
            if (cause instanceof DataIntegrityViolationException) {
                return new ResponseEntity<>("Duplicate entry found. Please use a unique value.", HttpStatus.BAD_REQUEST);
            } else if (cause instanceof ConstraintViolationException) {
                return new ResponseEntity<>("Constraint violation occurred. Please check your input.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Transaction error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // Handle runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // Handle no such element exceptions
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
