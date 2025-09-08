package org.example;


public interface LivroRepository {
  Livro findById(Long id);
  void update(Livro livro);
  void delete(Livro livro);
  Livro save(Livro livro);
}