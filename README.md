# 🏋️‍♂️ Projeto Academia - API com Quarkus

Este projeto é uma API REST para gerenciamento de treinos, exercícios e grupos musculares, desenvolvida com Quarkus.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Quarkus
- Hibernate ORM (Panache)
- RESTEasy (Jakarta REST)
- Jakarta Bean Validation
- Banco de Dados: H2 (desenvolvimento)
- OpenAPI (Swagger - SmallRye)
- Rate Limiting e Idempotência
- Versionamento de API (via URL)

---

## 📦 Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/G-u-i-l-h-e-r-m-e/API-TSI-Quarkus.git
```

### 2. Acesse a pasta do projeto

```bash
cd API-TSI-Quarkus
```

### 3. Crie dentro da pasta `resources` um arquivo chamado `import.sql`

### 4. Configure o banco de dados em `src/main/resources/application.properties`

```properties
quarkus.datasource.db-kind=h2
quarkus.hibernate-orm.scripts.generation.create-target=import.sql
quarkus.hibernate-orm.log.sql=true
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.theme=material
quarkus.smallrye-openapi.info-title=API de Treinos
quarkus.smallrye-openapi.info-version=2.0
quarkus.smallrye-openapi.info-contact-name=Equipe de Suporte
quarkus.smallrye-openapi.info-contact-email=suporte@senac.com.br
quarkus.smallrye-openapi.info-description=API desenvolvida para gerenciar treinos e exercicios fisicos. Permite cadastrar, listar, atualizar e excluir treinos, alem de associar exercicios a cada treino.
quarkus.http.cors=true
quarkus.http.cors.enabled=true
quarkus.http.cors.origins=*
quarkus.http.cors.methods=GET,POST,PUT,DELETE,PATCH,OPTIONS
quarkus.http.cors.headers=Accept,Authorization,Content-Type,X-requested-with,x-api-key,Idempotency-Key
quarkus.http.cors.exposed-headers=Authorization,Content-Type,x-api-key
```

### 5. Execute em modo de Desenvolvimento

```bash
./mvnw quarkus:dev
```

---

## 📝 Entidades e Endpoints - API v2

### 1.🏃‍♂️ Entidade Treino

* **Representação:**

  * `id` *(auto-gerado)*
  * `nome` *(obrigatório, mínimo 3 caracteres)*
  * `data` *(obrigatório, formato ISO 8601)*
  * `duracao` *(obrigatório, em minutos, valor positivo)*
  * `objetivo` *(opcional)*
  * `exercicios` *(opcional, JSON ou lista de strings)*
  * `notas` *(opcional)*
  * `usuario_id` *(obrigatório)*

* **Endpoints:**

  * `GET /api/v2/treinos`: Lista todos os treinos (limite de 3 req/10s).
  * `GET /api/v2/treinos/{id}`: Busca treino por ID.
  * `POST /api/v2/treinos`: Cria novo treino (requer cabeçalho `Idempotency-Key` e `X-API-Key`).
  * `PUT /api/v2/treinos/{id}`: Atualiza treino existente.
  * `DELETE /api/v2/treinos/{id}`: Remove treino por ID.
  * `GET /api/v2/treinos/busca/treino/{nome}`: Busca treinos por nome.

```json
{
  "id": 1,
  "nome": "Treino de Peito A",
  "notas": "Boa performance",
  "duracao": 60.0,
  "data": "2025-04-01T07:30:00",
  "objetivo": "Hipertrofia",
  "exercicios": ["Supino Reto", "Crucifixo", "Flexão de Braço"]
}
```

### 2. 🏋️ Entidade Exercício

* **Representação:**

  * `id` *(auto-gerado)*
  * `nome` *(obrigatório, único)*
  * `descricao` *(opcional)*
  * `musculos_principais` *(obrigatório)*
  * `musculos_secundarios` *(opcional)*
  * `tipo` *(obrigatório, valores: "FORCA", "CARDIO", "ALONGAMENTO")*
  * `instrucoes` *(opcional)*

* **Endpoints:**

  * `GET /api/v2/exercicios`: Lista todos os exercícios.
  * `GET /api/v2/exercicios/{id}`: Busca por ID.
  * `POST /api/v2/exercicios`: Cria novo exercício.
  * `PUT /api/v2/exercicios/{id}`: Atualiza exercício.
  * `DELETE /api/v2/exercicios/{id}`: Deleta exercício.
  * `GET /api/v2/exercicios/busca/exercicios/{nome}`: Busca por nome.

```json
{
  "id": 1,
  "nome": "Supino Reto",
  "descricao": "Exercício de empurrar com barra",
  "musculos_principais": "Peitoral",
  "musculos_secundarios": "Tríceps, Ombros",
  "tipo": "FORCA",
  "instrucoes": "Deite-se, segure a barra e empurre para cima"
}
```

### 3. 💪 Entidade Músculo

* **Representação:**

  * `id` *(auto-gerado)*
  * `nome` *(obrigatório, único)*
  * `grupo_muscular` *(obrigatório)*
  * `descricao` *(opcional)*

* **Endpoints:**

  * `GET /api/v2/musculos`: Lista todos os músculos.
  * `GET /api/v2/musculos/{id}`: Busca por ID.
  * `POST /api/v2/musculos`: Cria novo músculo.
  * `PUT /api/v2/musculos/{id}`: Atualiza músculo.
  * `DELETE /api/v2/musculos/{id}`: Remove músculo.
  * `GET /api/v2/musculos/busca/musculo/{nome}`: Busca por nome.

```json
{
  "id": 1,
  "nome": "Peitoral",
  "descricao": "Músculo responsável pela adução dos braços",
  "grupo_muscular": "Peito"
}
```

---

## 📘️ Documentação via Swagger UI

Acesse a documentação gerada automaticamente:

[http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)

---

## ✨ Funcionalidades Avançadas (v2)

| Recurso             | Descrição |
|---------------------|-----------|
| ✅ Versionamento     | Implementado via URL: `/api/v2/...` |
| ✅ Autenticação      | Requisição exige cabeçalho `X-API-Key` |
| ✅ Idempotência      | Requisições `POST` exigem `Idempotency-Key` |
| ✅ Rate Limiting     | 3 chamadas a cada 10s com fallback 429 |
| ✅ Bean Validation   | Campos obrigatórios validados automaticamente |
| ✅ Tratamento de Erros | Mensagens claras com códigos HTTP adequados |
| ✅ Swagger customizado com metadados|

---

📢 Dúvidas ou sugestões? Entre em contato com a equipe: `guilherme.asouza29@senacsp.edu.br`

🚀 Projeto desenvolvido para fins acadêmicos. Todos os direitos reservados.

