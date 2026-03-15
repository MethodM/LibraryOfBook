# Biblioteca API

API REST para gerenciamento de uma biblioteca de livros desenvolvida com
**Java e Spring Boot**.\
O projeto segue boas práticas de arquitetura backend, utilizando **DTOs,
tratamento global de exceções, validação e paginação**.

A aplicação permite **registrar, consultar, atualizar e remover
livros**, além de fornecer uma estrutura escalável para futuras
evoluções, incluindo **interface gráfica para usuários**.

------------------------------------------------------------------------

# Tecnologias Utilizadas

-   Java 17+
-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   Jakarta Validation
-   JUnit
-   MockMvc
-   Mockito
-   Maven
-   IntelliJ IDEA

------------------------------------------------------------------------

# Arquitetura do Projeto

O projeto segue uma arquitetura em camadas comum em aplicações backend
Java:

    controller
    service
    repository
    dto
    model
    exception
    mapper

### Controller

Responsável por receber as requisições HTTP, delegar a lógica de negócio para os serviços e retornar as respostas adequadas. Utiliza anotações como `@RestController` e `@RequestMapping` para definir os endpoints da API.

### Service

Contém a **lógica de negócio** da aplicação.

### Repository

Responsável pela **persistência de dados** usando Spring Data JPA.

### DTO

Utilizado para **transferência segura de dados** entre camadas.

### Exception

Tratamento global de erros utilizando `@RestControllerAdvice`.

------------------------------------------------------------------------

# Modelo da Entidade

### Livro

-   id
-   titulo
-   autor
-   anoPublicacao
-   disponivel

------------------------------------------------------------------------

# Endpoints da API

### Criar livro

POST `/livros`

Body:

``` json
{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "anoPublicacao": 2008
}
```

Resposta:

    201 CREATED

------------------------------------------------------------------------

### Buscar livro por ID

GET `/livros/{id}`

Resposta:

``` json
{
  "id": 1,
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "anoPublicacao": 2008,
  "disponivel": true
}
```

------------------------------------------------------------------------

### Atualizar livro

PUT `/livros/{id}`

------------------------------------------------------------------------

### Deletar livro

DELETE `/livros/{id}`

------------------------------------------------------------------------

### Listar livros com paginação

GET `/livros?page=0&size=10`

Parâmetros:

-   page → número da página
-   size → quantidade de registros
-   sort → ordenação

Exemplo:

    GET /livros?page=0&size=5&sort=titulo

------------------------------------------------------------------------

# Testes

A aplicação possui testes utilizando:

-   JUnit
-   MockMvc
-   Mockito

Os testes simulam requisições HTTP para os controllers.

------------------------------------------------------------------------

# Boas práticas aplicadas

✔ DTO Pattern\
✔ Exception Handling Global\
✔ Validação com `@Valid`\
✔ Paginação com `Pageable`\
✔ Separação em camadas\
✔ Testes de controller

------------------------------------------------------------------------

# Roadmap do Projeto

## Backend

-   [ ] Busca por título
-   [ ] Busca por autor
-   [ ] Ordenação dinâmica
-   [ ] Filtros avançados
-   [ ] Documentação com Swagger
-   [ ] Autenticação com Spring Security
-   [ ] Logs estruturados

## Infraestrutura

-   [ ] Banco de dados H2
-   [ ] Migrações com Flyway
-   [ ] Dockerização

## Frontend (GUI)

Planeja-se adicionar uma interface gráfica para interação com a API.

Possíveis tecnologias:

-   JavaFX
-   React
-   Angular
-   Vue

------------------------------------------------------------------------

# Objetivo do Projeto

Este projeto foi desenvolvido com fins de **estudo e prática de
desenvolvimento backend com Java e Spring Boot**, com foco em:

-   arquitetura limpa
-   boas práticas REST
-   testes automatizados
-   escalabilidade

------------------------------------------------------------------------
