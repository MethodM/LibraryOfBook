package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LivroController.class)
public class LivroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private LivroService livroService; //mocka a dependência do controller

  @Test
  public void testCriarLivro() throws Exception { //Retorna código 201 Created

    // GIVEN
    Livro livroEntrada = new Livro();
    livroEntrada.setTitulo("Teste de Livro");

    Livro livroSalvo = new Livro();
    livroSalvo.setId(1L);
    livroSalvo.setTitulo("Teste de Livro");

    given(livroService.registrarLivro(any(Livro.class)))
        .willReturn(livroSalvo);

    // WHEN
    ResultActions response = mockMvc.perform(
        post("/livros/criar-livro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(livroEntrada))
    );

    // THEN
    response
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Teste de Livro"));
  }

  @Test
  public void deveRetornarErroAoCriarLivroSemDados() throws Exception {
    //Given - Fazer os testes de pré-condição
    Livro livroInvalido = new Livro();
    livroInvalido.setDisponivel(false);

    given(livroService.registrarLivro(any(Livro.class)))
        .willThrow(new IllegalArgumentException("Dados do livro inválidos"));

    //When
    ResultActions response = mockMvc.perform(
        post("/livros/criar-livro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(livroInvalido))
    );

    //Then
    response
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.error").value("Dados do livro inválidos"));
  }

  @Test
  public void testLerLivro() throws Exception {
    //Given
    Long id = 1L;

    Livro livroParaLeitura = new Livro();
    livroParaLeitura.setId(id);
    livroParaLeitura.setAutor("Autor de Teste");
    livroParaLeitura.setTitulo("Livro de Teste");

    given(livroService.consultarLivro(id))
        .willReturn(livroParaLeitura);

    //When
    ResultActions response = mockMvc.perform(get("/livros/ler-livro/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(livroParaLeitura)));

    //Then
    response
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Livro de Teste"))
        .andExpect(jsonPath("$.autor").value("Autor de teste"));
  }

  @Test
  public void testLerLivroNaoEncontrado() throws Exception { //entrega 404
    // GIVEN
    Long id = 99L;

    given(livroService.consultarLivro(id))
        .willThrow(new LivroNaoEncontradoException(id));

    // WHEN
    ResultActions response = mockMvc.perform(
        get("/livros/ler-livro/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
    );

    // THEN
    response
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message")
            .value("Livro com id 99 não encontrado"));
  }

  @Test //indica uma anotação do JUnit "teste automatizado"
  public void testBuscarLivro() throws Exception {
    // GIVEN
    Long id = 1L;

    Livro livro = new Livro();
    livro.setId(id);
    livro.setTitulo("Teste");
    livro.setAutor("Autor");
    given(livroService.consultarLivro(1L)).willReturn(livro);

    // WHEN
    ResultActions response = mockMvc.perform(
        get("/livros/buscar-livro/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON));

    // THEN
    response
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Teste"))
        .andExpect(jsonPath("$.autor").value("Autor"));
  }

  @Test
  void deveRetornarErroAoBuscarLivroInexistente() throws Exception {

    // GIVEN (dado que...)
    Long idInexistente = 999L;

    given(livroService.consultarLivro(idInexistente))
        .willThrow(new LivroNaoEncontradoException(idInexistente));

    // WHEN (quando...)
    ResultActions response = mockMvc.perform(
        get("/livros/buscar-livro/{id}", idInexistente)
            .accept(MediaType.APPLICATION_JSON)
    );

    // THEN (então...)
    response.andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message")
            .value("Livro com id " + idInexistente + " não encontrado."));
  }

  @Test
  public void testAtualizarLivro() throws Exception {

    //Given
    Long id = 1L;
    Livro livroAtualizado = new Livro();
    livroAtualizado.setTitulo("Livro Atualizado");
    livroAtualizado.setAutor("Autor Atualizado");

    String jsonLivro = new ObjectMapper().writeValueAsString(livroAtualizado);

    given(livroService.atualizarDados(eq(1L), any()))
        .willReturn(livroAtualizado);

    //When
    ResultActions response = mockMvc.perform(put("/livros/atualizar-livro/{id}", id)//.param("id", "1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonLivro));

    //Then
    response.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.titulo").value("Livro Atualizado"))
        .andExpect(jsonPath("$.autor").value("Autor Atualizado"))
        .andExpect(content().json(jsonLivro));
  }

  @Test
  public void deveRetornarErroAoAtualizarLivroInexistente() throws Exception {
    // GIVEN
    Long idInexistente = 999L;
    Livro dadosLivro = new Livro();
    dadosLivro.setTitulo("Livro Inexistente");
    dadosLivro.setAutor("Autor Inexistente");

    String jsonLivro = new ObjectMapper().writeValueAsString(dadosLivro);

    given(livroService.atualizarDados(eq(idInexistente), any()))
        .willThrow(new LivroNaoEncontradoException(idInexistente));

    // WHEN
    ResultActions response = mockMvc.perform(
        put("/livros/atualizar-livro/{id}", idInexistente)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonLivro)
    );

    // THEN
    response.andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message")
            .value("Livro com id " + idInexistente + " não encontrado"));
  }
  
  @Test
  public void deletarLivro() throws Exception {
    //Given
    Long id = 1L;

    given(livroService.deletarLivro(id))
        .willReturn(new Livro());

    //WHEN
    ResultActions response = mockMvc.perform(delete("/livros/deletar-livro/{id}", id)
            .accept(MediaType.APPLICATION_JSON));

    //THEN
    response
        .andExpect(status().isOk())
        .andExpect(content().string("Livro deletado com sucesso."));
  }

  @Test
  public void deletarLivroInexistenteErro404() throws Exception {
    // GIVEN
    Long idInexistente = 999L;

    given(livroService.deletarLivro(idInexistente))
        .willThrow(new LivroNaoEncontradoException(idInexistente));

    // WHEN
    ResultActions response = mockMvc.perform(
        delete("/livros/deletar-livro/{id}", idInexistente)
            .accept(MediaType.APPLICATION_JSON)
    );

    // THEN
    response
        .andExpect(status().isNotFound())
        .andExpect(content().string("Livro com id 999 não encontrado"));
  }
}