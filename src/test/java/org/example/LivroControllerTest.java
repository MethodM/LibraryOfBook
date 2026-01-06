package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    mockMvc.perform(get("/livros/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().string("Detalhes do livro exibido."));
  }

  @Test //indica uma anotação do JUnit "teste automatizado"
  public void testBuscarLivro() throws Exception {
    // GIVEN
    Livro livro = new Livro(1L, "Teste", "Autor");
    given(livroService.buscarPorId(1L)).willReturn(livro);

    // WHEN
    mockMvc.perform(get("/livros/{id}", 1L))

        // THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Teste"));
  }

  @Test
  void deveRetornarErroAoBuscarLivroInexistente() throws Exception {

    // GIVEN (dado que...)
    Long idInexistente = 999L;

    given(livroService.consultarLivro(idInexistente))
        .willThrow(new LivroNaoEncontradoException(idInexistente));

    // WHEN (quando...)
    ResultActions response = mockMvc.perform(
        get("/livros/buscar-livro{id}", idInexistente)
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
}