package org.example;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroRepositoryImpl implements LivroRepository {

  List<Livro> listaLivros = new ArrayList<>();

  @Override
  public Livro findById(Long id) {
    return listaLivros.get(id);
  }

  @Override
  public void update(Livro livro) {
  }

  @Override
  public void delete(Livro livro) {

  }

  @Override
  public Livro save(Livro livro) {
    return null;
  }

  @Override
  public Livro listarLivros(Livro livro) {
    listaLivros.get(livro);
    return 
  }
}
