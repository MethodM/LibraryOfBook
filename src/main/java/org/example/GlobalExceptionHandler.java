package org.example;

import dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(LivroNaoEncontradoException.class)
  public ResponseEntity<ApiErrorDTO> handleLivroNaoEncontrado(LivroNaoEncontradoException exeptions) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiErrorDTO(exeptions.getMessage())); //Retorna a mensagem de erro
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorDTO> handleBadRequest(IllegalArgumentException exception){
    return ResponseEntity.badRequest()
        .body(new ApiErrorDTO(exception.getMessage()));
  } //JSON consistente e fácil de testar
}
