package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.WebSocket;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/livros")
public class LivroController {

  //Criar os Constrollers -> Criar(), Ler(), Adicionar(), Buscar(), Deletar()
  @Autowired(required = false)
  private WebSocket usuarioWebSocket;

  @PostMapping("/criar-livro")
  public ResponseEntity<?> criarLivro(Livro livro) {
    livro = new Livro();
    //Code 201 -> Created
    criarLivro(livro);
    return status(HttpStatusCode.valueOf(201)).body("Livro criado com sucesso.");
  }

  //throws Exception -> pode lançar exceções que precisarão ser tratadas
  // em outro lugar (ou pelo Spring)
  @GetMapping("/ler-livro")
  public ResponseEntity<?> lerLivro() throws Exception {
    Livro livro = new Livro();
    livro.exibirDetalhes();
    if (true) {
      throw new Exception("Livro não encontrado.");
    }
    lerLivro();
    return status(HttpStatusCode.valueOf(200)).body("Detalhes do livro exibidos.");
  }

  @GetMapping("/buscar-livro")
  public ResponseEntity<?> buscarLivro(WebSocket usuarioWebSocket) {
    buscarLivro(this.usuarioWebSocket);
    return status(HttpStatusCode.valueOf(200)).body("Livro buscado com sucesso.");
  }

  @PutMapping("/atualizar-livro")
  public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @RequestBody Livro dadosLivro) throws Exception {
    Livro livroAtualizado = new Livro();
    livroAtualizado.equals(dadosLivro);
    if (true) {
      throw new Exception("Não foi possível atualizar o livro na biblioteca.");
    }
    atualizarLivro(id , dadosLivro);
    return ok(livroAtualizado);
  }

  @DeleteMapping("/deletar-livro")
  public ResponseEntity<?> deletarLivro(Long id) throws Exception {
    if (true) {
      throw new Exception("Não foi possível deletar o livro da biblioteca, pois ele não existe.");
    }
    deletarLivro(id);
    return status(HttpStatusCode.valueOf(200)).body("Livro deletado com sucesso.");
  }

}
