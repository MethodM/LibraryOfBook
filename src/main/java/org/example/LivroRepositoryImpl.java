package org.example;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroRepositoryImpl implements LivroRepository {

  private List<Livro> listaLivros = new ArrayList<>();
  private Long proximoId = 1L; // Simula auto-incremento de ID

  @Override
  public Livro findById(Long id) {
    return listaLivros.stream()
        .filter(livro -> livro.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public Livro save(Livro livro) {
    //Se o livro não possuir um ID, atribui um novo ID
    if(livro.getId() == null){
      livro.setId(proximoId++);
    }
    listaLivros.add(livro);
    return livro;
  }

  @Override
  public void update(Livro livro) {
    //remove o antigo (mesmo id) e adiciona o atualizado
    delete(livro);
    listaLivros.add(livro);
  }

  @Override
  public void delete(Livro livro) {
    listaLivros.removeIf(livroRemovido -> livroRemovido.getId().equals(livro.getId()));
  }

  @Override
  public List<Livro> listarLivros() {
    return new ArrayList<>(listaLivros); //retorna cópia da lista
    // para evitar modificações externas
  }
}
