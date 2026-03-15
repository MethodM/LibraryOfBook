package org.example.dto;

public class LivroResponseDTO extends LivroCreateDTO {

  private Long id;
  private String titulo;
  private String autor;
  private int anoPublicacao;
  private boolean disponivel;

  public LivroResponseDTO() {
  }

  public LivroResponseDTO(Long id, String titulo, String autor, int anoPublicacao, boolean disponivel) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.anoPublicacao = anoPublicacao;
    this.disponivel = disponivel;
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getAutor() {
    return autor;
  }

  public Integer getAnoPublicacao() {
    return anoPublicacao;
  }

  public boolean isDisponivel() {
    return disponivel;
  }
}
