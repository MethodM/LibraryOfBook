package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LivroCreateDTO {

  @NotBlank
  private String titulo;

  @NotBlank
  private String autor;

  @NotNull(message = "Ano de publicação é obrigatório!")
  @Positive(message = "O ano de publicação deve ser um número positivo.")
  private Integer anoPublicacao;

  @NotNull
  private Boolean disponivel;

  public String getTitulo() {
    return titulo;
  }

  public String getAutor() {
    return autor;
  }

  public Integer getAnoPublicacao() {
    return anoPublicacao;
  }

  public boolean getDisponivel(){
    return disponivel;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public void setAnoPublicacao(Integer anoPublicacao) {
    this.anoPublicacao = anoPublicacao;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }
}
