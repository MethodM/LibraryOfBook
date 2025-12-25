package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/livros")
public class LivroController {

  //Criar os Constrollers -> Criar(), Ler(), Adicionar(), Buscar(), Deletar()
  /*@Autowired(required = false)
  private WebSocket usuarioWebSocket;*/

  @Autowired
  private LivroService livroService;

  @PostMapping("/criar-livro")
  public ResponseEntity<?> criarLivro(@RequestBody Livro livro) {
    Livro livroSalvo = livroService.registrarLivro(livro);
    //Code 201 -> Created
    return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    //return ResponseEntity.status(201).body(livroSalvo);
  }

  //throws Exception -> pode lançar exceções que precisarão ser tratadas
  // em outro lugar (ou pelo Spring)
  @GetMapping("/ler-livro/{id}")
  public ResponseEntity<?> lerLivro() throws Exception {
    Livro livro = new Livro();
    livro.exibirDetalhes();
    return ResponseEntity.status(HttpStatus.OK).body("Detalhes do livro exibidos.");
    //return status(HttpStatusCode.valueOf(200)).body("Detalhes do livro exibidos.");
  }

  @GetMapping("/buscar-livro/{id}")
  public ResponseEntity<?> buscarLivro(@PathVariable Long id) {
    Livro livroBuscado = livroService.consultarLivro(id);
    if (livroBuscado == null) {
      return ResponseEntity.status(404).body("Livro não encontrado na biblioteca.");
    }
    // buscarLivro(this.usuarioWebSocket); -> errado: chamando a si mesmo de novo
    return ResponseEntity.ok(livroBuscado);
  }

  @PutMapping("/atualizar-livro/{id}")
  public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @RequestBody Livro dadosLivro) throws Exception {
    Livro livroAtualizado = livroService.atualizarDados(id, dadosLivro);
    if (livroAtualizado == null) {
      return ResponseEntity.status(404).body("Não foi possível atualizar o livro na biblioteca.");
    }
    return ResponseEntity.ok(livroAtualizado);
  }

  @DeleteMapping("/deletar-livro/{id}")
  public ResponseEntity<?> deletarLivro(@PathVariable Long id) throws Exception {
    boolean livroDeletado = livroService.deletarLivro(id) != null;
    if (!livroDeletado) {
      return ResponseEntity.status(404).body("Não foi possível deletar o livro da biblioteca, pois ele não existe.");
    }
    return ResponseEntity.ok("Livro deletado com sucesso.");
  }

}
