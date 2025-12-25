package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private LivroService livroService;

  @Test
  public void testCriarLivro() throws Exception {
    Livro livroTest = new Livro();
    livroTest.setTitulo("Teste de Livro");

    mockMvc.perform(post("/livros/criar-livro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(livroTest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.titulo").value("Teste"));
    //expressão para dispor sobre id e titulo
  }

  @Test
  public void deveRetornarErroAoCriarLivroSemDados() throws Exception {
    mockMvc.perform(post("/livros/criar-livro"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testLerLivro() throws Exception {
    mockMvc.perform(get("/livros/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().string("Detalhes do livro exibido."));
  }

  @Test //indica uma anotação do JUnit "teste automatizado"
  public void testBuscarLivro() throws Exception {
    mockMvc.perform(get("/livros/buscar-livro"))
        .andExpect(status().isOk())
        .andExpect(content().string("Livro encontrado com sucesso."));
  }

  @Test
  public void deveRetornarErroAoBuscarLivroInexistente() throws Exception {
    mockMvc.perform(get("/livros/buscar-livro").param("id", "9999"))
        .andExpect(status().isBadRequest()) //Apresenta o status de não encontrado - código 400 Bad Request"
        .andExpect(content().string("Livro não encontrado na biblioteca."))
        .andExpect(status().isNotFound())
        .andExpect(status().isInternalServerError());
  }
  //Poderia ser assim também, para ficar mais claro:
  /*@Test
  void dadoLivroInexistente_quandoBuscar_entaoRetornaBadRequest() throws Exception {
    // GIVEN (dado que...)
    String idInexistente = "9999";

    // WHEN (quando...)
    ResultActions response = mockMvc.perform(
        get("/livros/buscar-livro")
            .param("id", idInexistente)
    );

    // THEN (então...)
    response.andExpect(status().isNotFound())
    .andExpect(content().string("Livro com id " + idInexistente + " não encontrado."));;
  }*/
  @Test
  public void testAtualizarLivro() throws Exception {

    //Given
    Long id = 1L;
    Livro livroAtualizado = new Livro();
    livroAtualizado.setTitulo("Livro Atualizado");
    livroAtualizado.setAutor("Autor Atualizado");

    String jsonLivro = new ObjectMapper().writeValueAsString(livroAtualizado);

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
