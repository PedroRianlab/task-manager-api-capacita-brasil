# Task Manager API

API RESTful para gerenciamento de tarefas, desenvolvida com Spring Boot 3, Spring Data JPA, H2 Database e Spring Security com JWT.

## Pré-requisitos

- Java 17 ou superior
- Git
- Maven (opcional, pois o projeto possui Maven Wrapper)

Confira a versão do Java:

```bash
java -version
```

## Como iniciar o projeto

Clone o repositório e entre na pasta do projeto:

```bash
git clone <URL_DO_REPOSITORIO>
cd task-manager-api
```

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

A API será iniciada em:

```text
http://localhost:8080
```

O banco H2 é criado em memória e suas tabelas são geradas automaticamente quando a aplicação inicia. Como o banco é volátil, os dados são perdidos ao reiniciar a aplicação.

## Como executar os testes

### Windows

```powershell
.\mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```

Para gerar o pacote da aplicação:

```bash
.\mvnw.cmd clean package
```

Depois, execute o JAR gerado:

```bash
java -jar target/task-manager-api-0.0.1-SNAPSHOT.jar
```

## Documentação Swagger

Com a aplicação em execução, acesse:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Especificação OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

No Swagger UI, use o token retornado pelo login no botão **Authorize**, informando:

```text
Bearer SEU_TOKEN
```

## H2 Console

O console do banco está disponível em:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Use os seguintes dados:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:taskdb` |
| Usuário | `sa` |
| Senha | deixe em branco |

## Fluxo de uso da API

### 1. Cadastrar usuário

`POST /api/auth/register`

```json
{
  "nome": "Maria",
  "email": "maria@email.com",
  "senha": "senha123"
}
```

### 2. Fazer login

`POST /api/auth/login`

```json
{
  "email": "maria@email.com",
  "senha": "senha123"
}
```

A resposta contém um JWT:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Envie esse token nas requisições protegidas:

```text
Authorization: Bearer SEU_TOKEN
```

### 3. Criar uma tarefa

`POST /api/v1/tarefas`

```json
{
  "descricao": "Estudar Spring Security",
  "prioridade": "ALTA"
}
```

### 4. Listar tarefas

`GET /api/v1/tarefas`

Para filtrar pelo status de conclusão:

```text
GET /api/v1/tarefas?concluida=false
```

### 5. Buscar, atualizar e excluir uma tarefa

```text
GET    /api/v1/tarefas/{id}
PUT    /api/v1/tarefas/{id}
DELETE /api/v1/tarefas/{id}
```

O corpo do `PUT` utiliza o mesmo formato da criação:

```json
{
  "descricao": "Estudar Spring Security e JWT",
  "prioridade": "MEDIA"
}
```

As operações de tarefas exigem autenticação. Cada usuário só pode consultar, alterar ou excluir suas próprias tarefas.

## Principais endpoints

| Método | Endpoint | Autenticação |
|---|---|---|
| `POST` | `/api/auth/register` | Não |
| `POST` | `/api/auth/login` | Não |
| `POST` | `/api/v1/tarefas` | JWT |
| `GET` | `/api/v1/tarefas` | JWT |
| `GET` | `/api/v1/tarefas/{id}` | JWT |
| `PUT` | `/api/v1/tarefas/{id}` | JWT |
| `DELETE` | `/api/v1/tarefas/{id}` | JWT |

## Configurações

As configurações principais estão em `src/main/resources/application.properties`:

- Porta padrão: `8080`
- Banco: H2 em memória
- Segredo JWT: propriedade `jwt.secret`
- Expiração do token: propriedade `jwt.expiration`

Em ambientes reais, altere o segredo JWT por uma variável de ambiente ou outro mecanismo seguro de configuração.
