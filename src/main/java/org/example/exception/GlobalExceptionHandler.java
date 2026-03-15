package org.example.exception;

import org.example.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 404
  @ExceptionHandler(LivroNaoEncontradoException.class)
  public ResponseEntity<ApiErrorDTO> handleLivroNaoEncontrado(LivroNaoEncontradoException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ApiErrorDTO(ex.getMessage()));
  }

  // 400 regra de negócio
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorDTO> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ApiErrorDTO(ex.getMessage()));
  }

  // 400 validação DTO
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }
}