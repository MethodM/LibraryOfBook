package org.example;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Livro {

  private Long id;
  private String titulo;
  private String autor;
  private int anoPublicacao;
  private boolean disponivel;

  public Livro() {

  }

  public Livro(Long id, String titulo, String autor, int anoPublicacao, boolean disponivel) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.anoPublicacao = anoPublicacao;
    this.disponivel = true;
  }

  public Livro(Long id, String titulo, String autor, int anoPublicacao) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.anoPublicacao = anoPublicacao;
    this.disponivel = disponivel;
  }

  public Long getId() {
    return id;
  }

  public void exibirDetalhes() {
    System.out.println("Teste: Detalhes do livro");
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getTitulo() {
    return titulo;
  }

  public static class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(Long id) {
      super(id + " não encontrado.");
    }
  }
}
