package org.example.repository;


import org.example.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
  /*Optional<Livro> findById(Long id);

  void update(Livro livro);

  void delete(Livro livro);

  Livro save(Livro livro);

  List<Livro> listarLivros();*/
}