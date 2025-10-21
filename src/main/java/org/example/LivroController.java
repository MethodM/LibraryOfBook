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

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/livros")
public class LivroController {

  //Criar os Constrollers -> Criar(), Ler(), Adicionar(), Buscar(), Deletar()
  @Autowired(required = false)
  private WebSocket usuarioWebSocket;
  private LivroService livroService;

  @PostMapping("/criar-livro")
  public ResponseEntity<?> criarLivro(@RequestBody Livro livro) {
    Livro livroSalvo = livroService.registrarLivro(livro.getId(), livro);
    //Code 201 -> Created
    return ResponseEntity.status(201).body(livroSalvo);
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
    return status(HttpStatusCode.valueOf(200)).body("Detalhes do livro exibidos.");
  }

  @GetMapping("/buscar-livro")
  public ResponseEntity<?> buscarLivro(WebSocket usuarioWebSocket, Long id) {
    Livro livroBuscado = livroService.consultarLivro(id);
    if (livroBuscado == null) {
      return ResponseEntity.status(404).body("Livro não encontrado na biblioteca.");
    }
    // buscarLivro(this.usuarioWebSocket); -> errado: chamando a si mesmo de novo
    return status(HttpStatusCode.valueOf(200)).body("Livro buscado com sucesso.");
  }

  @PutMapping("/atualizar-livro")
  public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @RequestBody Livro dadosLivro) throws Exception {
    Livro livroAtualizado = livroService.atualizarDados(id, dadosLivro);
    if (livroAtualizado == null) {
      return ResponseEntity.status(404).body("Não foi possível atualizar o livro na biblioteca.");
    }
    return ResponseEntity.ok(livroAtualizado);
  }

  @DeleteMapping("/deletar-livro")
  public ResponseEntity<?> deletarLivro(Long id) throws Exception {
    boolean livroDeletado = livroService.deletarLivro(id) != null;
    if (!livroDeletado) {
      return ResponseEntity.status(404).body("Não foi possível deletar o livro da biblioteca, pois ele não existe.");
    }
    return ResponseEntity.ok("Livro deletado com sucesso.");
  }

}
