# Delivery Tech API

 Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias

- **Java 21 LTS** (versão mais recente)
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- H2 Database (Banco em memória)
- Maven

## ⚡ Recursos Modernos Utilizados

- **Records** (Java 14+) para DTOs concisos (ver `HealthController.java`)
- `Map.of()` para criação de mapas imutáveis.
- Padrão de Camadas (Controller, Service, Repository).

## 🏃‍♂️ Como executar

1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. A aplicação estará disponível em `http://localhost:8080`

## 📋 Endpoints da API

### Health & Info
* `GET /health` - Status da aplicação (inclui versão Java)
* `GET /info` - Informações da aplicação
* `GET /h2-console` - Console do banco H2 (JDBC URL: `jdbc:h2:mem:deliverydb`)

### Clientes (`/clientes`)
* `POST /clientes` - Cadastra um novo cliente.
* `GET /clientes` - Lista todos os clientes ativos.
* `GET /clientes/{id}` - Busca um cliente por ID.
* `PUT /clientes/{id}` - Atualiza os dados de um cliente.
* `DELETE /clientes/{id}` - Inativa um cliente (Soft Delete).

### Restaurantes (`/restaurantes`)
* `POST /restaurantes` - Cadastra um novo restaurante.
* `GET /restaurantes` - Lista todos os restaurantes ativos.
* `GET /restaurantes/{id}` - Busca um restaurante por ID.
* `GET /restaurantes/categoria?nome={nome}` - Busca restaurantes ativos por nome de categoria.
* `PUT /restaurantes/{id}` - Atualiza os dados de um restaurante.
* `DELETE /restaurantes/{id}` - Inativa um restaurante (Soft Delete).

### Produtos (`/produtos`)
* `POST /produtos/restaurante/{id}` - Cadastra um novo produto para um restaurante.
* `GET /produtos/restaurante/{id}` - Lista todos os produtos de um restaurante.
* `GET /produtos/restaurante/{id}/disponiveis` - Lista apenas produtos disponíveis de um restaurante.
* `GET /produtos/{id}` - Busca um produto por ID.
* `PUT /produtos/{id}` - Atualiza os dados de um produto.
* `PATCH /produtos/{id}/disponibilidade?disponivel={true|false}` - Atualiza a disponibilidade de um produto.

### Pedidos (`/pedidos`)
* `POST /pedidos?clienteId={id}&restauranteId={id}` - Cria um novo pedido para um cliente e restaurante.
* `GET /pedidos/{id}` - Busca um pedido por ID.
* `GET /pedidos/cliente/{id}` - Lista todos os pedidos de um cliente.
* `PATCH /pedidos/{id}/status?status={novoStatus}` - Atualiza o status de um pedido (ex: CONFIRMADO, ENTREGUE).

## 👨‍💻 Desenvolvedor

- [Richard Lopes] - [IBMR]

---
 Desenvolvido com JDK 21 e Spring Boot 3.5.7