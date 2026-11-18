package com.cg.order.exception;

import com.cg.order.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(
            CustomerNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ErrorResponse> handleInventoryException(
            InsufficientInventoryException ex) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrder(
            InvalidOrderException ex) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationException(
            MethodArgumentNotValidException ex) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        message,
                        LocalDateTime.now()));
    }

    @ExceptionHandler(
            DuplicateRequestException.class)
    public ResponseEntity<ErrorResponse>
    handleDuplicate(
            DuplicateRequestException ex) {

        return ResponseEntity.status(
                        HttpStatus.CONFLICT)
                .body(
                        new ErrorResponse(
                                409,
                                ex.getMessage(),
                                LocalDateTime.now()));
    }
}
