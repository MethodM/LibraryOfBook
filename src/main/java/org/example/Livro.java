package org.example;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Livro {

  private Long id;

  @NotBlank(message = "O título do livro é obrigatório.")
  private String titulo;

  @NotBlank(message = "O autor do livro é obrigatório.")
  private String autor;

  @Positive(message = "O ano de publicação deve ser um número positivo.")
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

  public Livro(Long id, String titulo, String autor) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
  }

  public void exibirDetalhes() {
    System.out.println("Teste: Detalhes do livro");
  }
}
