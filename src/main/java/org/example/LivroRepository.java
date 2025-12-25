package org.example;


import java.util.List;
import java.util.Optional;

public interface LivroRepository {
  Optional<Livro> findById(Long id);

  void update(Livro livro);

  void delete(Livro livro);

  Livro save(Livro livro);

  List<Livro> listarLivros();
}