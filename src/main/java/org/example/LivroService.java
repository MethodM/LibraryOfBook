package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

  //Atributo livroRepository
  private final LivroRepository livroRepository;

  public LivroService(LivroRepository livroRepository) {
    this.livroRepository = livroRepository;
  }

  public Livro atualizarDados(Long id, Livro dadosLivro) {
    //Lógica para atualizar os livros da biblioteca
    Livro livroExistente = livroRepository.findById(id);

    if (livroExistente == null) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    }
    livroExistente.setTitulo(dadosLivro.getTitulo());
    livroExistente.setAutor(dadosLivro.getAutor());
    livroExistente.setAnoPublicacao(dadosLivro.getAnoPublicacao());
    livroExistente.setDisponivel(dadosLivro.isDisponivel());

    livroRepository.update(livroExistente);
    return livroExistente;
  }

  public Livro registrarLivro(Long id, Livro dadosLivro) {
    return livroRepository.save(dadosLivro);
  }

  public Livro deletarLivro(Long id) {
    Livro livroDeletado = livroRepository.findById(id);
    if (livroDeletado == null) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    } else {
      livroRepository.delete(livroDeletado);
      return livroDeletado;
    }
  }

  //Consultar livro por ID
  public Livro consultarLivro(Long id) {
    Livro livroConsultado = livroRepository.findById(id);
    if (livroConsultado == null) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    } else {
      return livroConsultado;
    }
  }

  public Livro atualizarDisponibilidade(Long id, boolean disponivel) {
    Livro livroAtualizado = livroRepository.findById(id);
    if (livroAtualizado == null) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    } else {
      livroAtualizado.setDisponivel(disponivel);
      livroRepository.update(livroAtualizado);
      livroAtualizado.getTitulo();
      return livroAtualizado;
    }
  }

  public List<Livro> listarLivros() {
    return livroRepository.listarLivros();
  }
}
