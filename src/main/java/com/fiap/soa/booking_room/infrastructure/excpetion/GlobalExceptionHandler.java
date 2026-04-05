package com.fiap.soa.booking_room.infrastructure.excpetion;

import com.fiap.soa.booking_room.dto.error.ErrorResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<ErrorResponseDto> duplicatedEntityException(DuplicatedEntityException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(409)
                .messsage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(401)
                .messsage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> businessException(BusinessException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(ex.getStatus().value())
                .messsage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> validationException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(400)
                .messsage(msg)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> genericException(Exception ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(500)
                .messsage("Erro interno no servidor")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}