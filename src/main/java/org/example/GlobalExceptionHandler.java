package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(LivroNaoEncontradoException.class)
  public ResponseEntity<String> handleLivroNaoEncontrado(LivroNaoEncontradoException exeptions) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(exeptions.getMessage()); //Retorna a mensagem de erro
  }
}
