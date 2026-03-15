package org.example.mapper;

import org.example.dto.LivroCreateDTO;
import org.example.dto.LivroResponseDTO;
import org.example.model.Livro;

public class LivroMapper {

  public static Livro toEntity(LivroCreateDTO dto) {
    Livro livro = new Livro();
    livro.setTitulo(dto.getTitulo());
    livro.setAutor(dto.getAutor()); // Corrigido para usar o getter correto
    livro.setAnoPublicacao(dto.getAnoPublicacao());
    livro.setDisponivel(true); // Define como disponível por padrão
    return livro;
  }

  public static LivroResponseDTO toDTO(Livro livro) {
    return new LivroResponseDTO(
        livro.getId(),
        livro.getTitulo(),
        livro.getAutor(),
        livro.getAnoPublicacao(),
        livro.isDisponivel()
    );
  }
}
