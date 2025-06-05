
# 🏋️‍♂️ Projeto Academia - API com Quarkus

API RESTful para gerenciamento de treinos, exercícios e grupos musculares, construída com Quarkus. Disponível em múltiplas versões (v1 e v2), com suporte a recursos avançados como autenticação, rate limiting e idempotência.

---

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

## ⚙️ Como Executar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/G-u-i-l-h-e-r-m-e/API-TSI-Quarkus.git
cd API-TSI-Quarkus
```

2. Crie o arquivo `import.sql`:

```bash
touch src/main/resources/import.sql
```

3. Configure o `application.properties`:

```properties
quarkus.datasource.db-kind=h2
quarkus.hibernate-orm.database.generation=create-drop
quarkus.hibernate-orm.scripts.generation.create-target=import.sql
quarkus.hibernate-orm.log.sql=true

quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.theme=material
quarkus.smallrye-openapi.info-title=API de Treinos
quarkus.smallrye-openapi.info-version=2.0
quarkus.smallrye-openapi.info-description=API RESTful com controle de treinos, exercícios e músculos. Inclui autenticação, rate limit e versionamento.

quarkus.http.cors=true
quarkus.http.cors.enabled=true
quarkus.http.cors.origins=*
quarkus.http.cors.methods=GET,POST,PUT,DELETE,PATCH,OPTIONS
quarkus.http.cors.headers=Accept,Authorization,Content-Type,X-requested-with,x-api-key,Idempotency-Key
quarkus.http.cors.exposed-headers=Authorization,Content-Type,x-api-key
```

4. Rode o projeto em modo dev:

```bash
./mvnw quarkus:dev
```

---

## 🔐 Segurança e Controle

- Autenticação via `X-API-Key` em todas as requisições.
- Prevenção de duplicidade com `Idempotency-Key` nos métodos `POST`.
- Rate Limiting: 3 requisições a cada 10 segundos.
- Validações automáticas com Bean Validation.

---

## 📘 Documentação das Versões

### ✅ V1 – Endpoints Base (sem autenticação)

- `GET /treinos`
- `GET /treinos/{id}`
- `POST /treinos`
- `PUT /treinos/{id}`
- `DELETE /treinos/{id}`
- `GET /treinos/busca/treino/{nome}`

- `GET /exercicios`, `GET /exercicios/{id}`, etc.
- `GET /musculos`, `GET /musculos/{id}`, etc.

> 💡 Versão básica para testes locais. Sem segurança de produção. Não usa prefixo `/api`.

---

### ✅ V2 – Endpoints com Recursos Avançados

- Prefixo: `/api/v2/`

#### Treinos

- `GET /api/v2/treinos`
- `GET /api/v2/treinos/{id}`
- `POST /api/v2/treinos`
- `PUT /api/v2/treinos/{id}`
- `DELETE /api/v2/treinos/{id}`
- `GET /api/v2/treinos/busca/treino/{nome}`

Exemplo JSON:
```json
{
  "nome": "Treino de Força",
  "duracao": 60,
  "objetivo": "Hipertrofia",
  "notas": "Alongar antes",
  "data": "2025-06-05T08:00:00",
  "exercicios": "[\"Supino\", \"Agachamento\"]"
}
```

#### Exercícios

- `GET /api/v2/exercicios`
- `GET /api/v2/exercicios/{id}`
- `POST /api/v2/exercicios`
- `PUT /api/v2/exercicios/{id}`
- `DELETE /api/v2/exercicios/{id}`
- `GET /api/v2/exercicios/busca/exercicio/{nome}`

Exemplo JSON:
```json
{
  "nome": "Supino Reto",
  "descricao": "Exercício de empurrar com barra",
  "musculos_principais": "Peitoral",
  "musculos_secundarios": "Tríceps, Ombros",
  "tipo": "Força",
  "instrucoes": "Deite-se, segure a barra e empurre para cima"
}
```

#### Músculos

- `GET /api/v2/musculos`
- `GET /api/v2/musculos/{id}`
- `POST /api/v2/musculos`
- `PUT /api/v2/musculos/{id}`
- `DELETE /api/v2/musculos/{id}`
- `GET /api/v2/musculos/busca/musculo/{nome}`

Exemplo JSON:
```json
{
  "nome": "Peitoral",
  "descricao": "Músculo responsável pela adução dos braços",
  "grupo_muscular": "Peito"
}
```

---

## 📄 Acessando a Documentação Swagger

Acesse: [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

- Interface interativa com **exemplos de payloads**
- Resumos de **códigos de resposta**
- Organizada por **versão da API**

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
