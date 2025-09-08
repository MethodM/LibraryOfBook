package org.example;


import java.util.List;

public interface LivroRepository {
  Livro findById(Long id);
  void update(Livro livro);
  void delete(Livro livro);
  Livro save(Livro livro);
  List<Livro> listarLivros();
}