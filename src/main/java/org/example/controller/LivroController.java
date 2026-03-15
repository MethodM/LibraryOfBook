package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.LivroCreateDTO;
import org.example.dto.LivroResponseDTO;
import org.example.model.Livro;
import org.example.service.LivroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {

  //Criar os Constrollers -> Criar(), Ler(), Adicionar(), Buscar(), Deletar()
  /*@Autowired(required = false)
  private WebSocket usuarioWebSocket;*/

  private LivroService livroService;

  public LivroController(LivroService livroService) {
    this.livroService = livroService;
  }

  @PostMapping("/criar-livro")
  public ResponseEntity<LivroResponseDTO> criarLivro(@Valid @RequestBody LivroCreateDTO livroDTO) {
    Livro livro = new Livro();
    livro.setTitulo(livroDTO.getTitulo());
    livro.setAutor(livroDTO.getAutor());
    livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
    livro.setDisponivel(true);

    Livro livroSalvo = livroService.registrarLivro(livro);
    return ResponseEntity.status(HttpStatus.CREATED).body(livroService.toResponseDTO(livroSalvo)); //Code 201 -> Created
  }

  //throws Exception -> pode lançar exceções que precisarão ser tratadas
  // em outro lugar (ou pelo Spring)
  @GetMapping("/ler-livro/{id}")
  public ResponseEntity<LivroResponseDTO> lerLivro(@PathVariable Long id) throws Exception {
    Livro livro = livroService.consultarLivro(id);
    return ResponseEntity.ok(livroService.toResponseDTO(livro));
    /*livro.exibirDetalhes();*/
    //return ResponseEntity.ok(livro);
  }

  @GetMapping("/buscar-livro/{id}")
  public ResponseEntity<LivroResponseDTO> buscarLivro(@PathVariable Long id) {
    Livro livroBuscado = livroService.consultarLivro(id);

    // buscarLivro(this.usuarioWebSocket); -> errado: chamando a si mesmo de novo
    return ResponseEntity.ok(livroService.toResponseDTO(livroBuscado)); //está correto -OK
  }

  @PutMapping("/atualizar-livro/{id}")
  public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @RequestBody Livro dadosLivro) {
    Livro livroAtualizado = livroService.atualizarDados(id, dadosLivro);
    /*if (livroAtualizado == null) {
      return ResponseEntity.status(404).body("Não foi possível atualizar o livro na biblioteca.");
    } -> até que plausível */
    return ResponseEntity.ok(livroAtualizado);
  }

  @DeleteMapping("/deletar-livro/{id}")
  public ResponseEntity<?> deletarLivro(@PathVariable Long id) {
    livroService.deletarLivro(id);
    return ResponseEntity.ok("Livro deletado com sucesso.");
  }

  @GetMapping
  public Page<LivroResponseDTO> listarLivros(Pageable pageable) {
    return livroService.listar(pageable);
  }
}
