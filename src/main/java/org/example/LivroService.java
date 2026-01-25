package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

  //Atributo livroRepository
  private final LivroRepository livroRepository;

  public LivroService(LivroRepository livroRepository) {
    this.livroRepository = livroRepository;
  }

  public Livro atualizarDados(Long id, Livro dadosLivro) {
    //Lógica para atualizar os livros da biblioteca
    Optional<Livro> livroExistente = livroRepository.findById(id);

    if (livroExistente.isEmpty()) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    }
    livroExistente.get().setTitulo(dadosLivro.getTitulo());
    livroExistente.get().setAutor(dadosLivro.getAutor());
    livroExistente.get().setAnoPublicacao(dadosLivro.getAnoPublicacao());
    livroExistente.get().setDisponivel(dadosLivro.isDisponivel());

    livroRepository.update(livroExistente.orElse(null));
    return livroExistente.orElse(null);
  }

  public Livro registrarLivro(Livro livro) {
    return livroRepository.save(livro);
  }

  public Livro deletarLivro(Long id) {
    Optional<Livro> livroDeletado = livroRepository.findById(id);
    if (livroDeletado.isEmpty()) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    } else {
      livroRepository.delete(livroDeletado.orElse(null));
      return livroDeletado.orElse(null);
    }
  }

  //Consultar livro por ID
  public Livro consultarLivro(Long id) {
    return livroRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + id));
  }

  public Livro atualizarDisponibilidade(Long id, boolean disponivel) {
    Optional<Livro> livroAtualizado = livroRepository.findById(id);
    if (livroAtualizado.isEmpty()) {
      throw new ResourceNotFoundException("Livro não encontrado com o ID: " + id);
    } else {
      livroAtualizado.get().setDisponivel(disponivel);
      livroRepository.update(livroAtualizado.orElse(null));
      livroAtualizado.get().getTitulo();
      return livroAtualizado.orElse(null);
    }
  }

  public List<Livro> listarLivros() {
    return livroRepository.listarLivros();
  }

  public Livro buscarPorId(Long id) {
    return livroRepository.findById(id)
        .orElseThrow(() -> new LivroNaoEncontradoException(id));
  }
}
