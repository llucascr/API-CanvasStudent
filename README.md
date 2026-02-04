# 🎓 API CanvasStudent

Plataforma de gerenciamento acadêmico com integração à API do Canvas LMS. Desenvolvida em **Java** com **Spring Boot**, permite o gerenciamento de usuários, matérias, tarefas e notas em um ambiente centralizado.

---

## 🚀 Funcionalidades

- 🔐 Criação de contas automaticamente a partir do token do Canvas.
- 🔄 Sincronização de matérias cadastradas na plataforma Canvas.
- 📝 Cadastro de notas com pesos personalizados.
- 📊 Cálculo automático de notas finais.
- ✅ Gerenciamento de tarefas feitas ou pendentes (estilo to-do list).

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **Flyway** – versionamento de banco de dados
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok**
- **ModelMapper**
- **Jackson**
- **Canvas LMS API**

---

## 🔄 Como funciona a integração com o Canvas

A partir do token de autenticação do usuário no Canvas, a API:

1. Recupera os dados do usuário (ID e nome).
2. Busca todas as matérias (cursos) vinculadas.
3. Cria/atualiza o cadastro local do usuário com suas matérias.
4. Permite que o usuário associe notas, pesos e tarefas aos cursos sincronizados.

## Modelo de Objetos

![Modelo de Objetos](https://github.com/llucascr/API-CanvasStudent/canvas.student/img/Driagrams-ApiCanvasStudent-Modelo%20de%20Objeto.drawio.png)

## 📁 Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── api/
│   │           └── canvas/
│   │               └── student/
│   │                   ├── controller/       # Endpoints da API
│   │                   ├── service/          # Regras de negócio
│   │                   ├── dto/              # Objetos de transferência de dados
│   │                   ├── model/            # Entidades JPA
│   │                   ├── repository/       # Interfaces de acesso ao banco
│   │                   ├── config/           # Configurações gerais (CORS, Swagger, Beans, etc.)
│   │                   ├── exception/        # Tratamento de exceções personalizadas
│   │                   └── handler/          # Classes que lidam com exceções globais
│   └── resources/
│       ├── application.yml                   # Configurações da aplicação
│       └── db/
│           └── migration/                    # Scripts de migração Flyway
