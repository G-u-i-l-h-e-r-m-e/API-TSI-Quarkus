
# 🏋️‍♂️ Projeto Academia - API com Quarkus

Este projeto é uma API REST para gerenciamento de treinos, exercícios e grupos musculares, desenvolvida com Quarkus.

## 🚀 Tecnologias Utilizadas

- Java 17+
- [Quarkus](https://quarkus.io/)
- Hibernate ORM (com Panache)
- RESTEasy
- Jakarta Validation
- PostgreSQL (ou o banco que você estiver usando)
- Swagger/OpenAPI (SmallRye)

---

## 📦 Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/G-u-i-l-h-e-r-m-e/API-TSI-Quarkus.git
```
### 2. Abra o arquivo
```bash
cd API-TSI-Quarkus
```
### 3. Crie dentro da pasta resources um arquivo chamado "import.sql"
### 4. Configure o banco de dados
No arquivo src/main/resources/application.properties
#### Adicione:
```bash
quarkus.datasource.db-kind=h2
quarkus.hibernate-orm.scripts.generation.create-target=import.sql
quarkus.hibernate-orm.log.sql=true
quarkus.swagger-ui.always-include=true
```
### 5. Execute em modo de Desenvolvimento
```bash
./mvnw quarkus:dev
```
## 📍 Entidades e Endpoints
### 1.🏃‍♂️ Entidade Treino
- **Representação:** 
    - `id` (identificador único do treino)
    - `nome` (nome do treino, por exemplo, "Treino de Força A", "Cardio da Manhã")
    - `data` (data em que o treino foi realizado ou está agendado)
    - `duracao` (duração do treino em minutos)
    - `objetivo` (objetivo do treino, por exemplo, "Hipertrofia", "Perda de Peso")
    - `exercicios` (uma lista de exercícios incluídos no treino, possivelmente referenciando a entidade "Exercício")
    - `notas` (observações sobre o treino)
    - `usuario_id` (identificador do usuário que realizou o treino)
- **Endpoints:**
    - `GET /treinos`: Retorna uma lista de todos os treinos.
    - `GET /treinos/{id}`: Retorna um treino específico pelo ID.
    - `POST /treinos`: Cria um novo treino.
    - `PUT /treinos/{id}`: Atualiza um treino existente pelo ID.
    - `DELETE /treinos/{id}`: Deleta um treino pelo ID.
    - `GET /treinos/busca/treino/{nome}`: Faz uma busca dos treinos, com base no nome.

  ### Estrutura JSON:
```bash
{
    "id": 1,
    "nome": "Treino de Peito A",
    "notas": "Boa performance",
    "duracao": 60.0,
    "data": "2025-04-01T07:30:00",
    "objetivo": "Hipertrofia",
    "exercicios": "[\"Supino Reto\", \"Crucifixo\", \"Flexão de Braço\"]"
  }
```
### 2. 🏋️ Entidade Exercício
- **Representação:** 
    - `id` (identificador único do exercício)
    - `nome` (nome do exercício, por exemplo, "Agachamento", "Supino Reto", "Corrida")
    - `descricao` (descrição detalhada do exercício)
    - `musculos_principais` (uma lista de músculos primários trabalhados no exercício, possivelmente referenciando a entidade "Músculo")
    - `musculos_secundarios` (uma lista de músculos secundários trabalhados no exercício)
    - `tipo` (tipo de exercício, por exemplo, "Força", "Cardio", "Alongamento")
    - `instrucoes` (instruções passo a passo para realizar o exercício)
- **Endpoints:**
    - `GET /exercicios`: Retorna uma lista de todos os exercícios.
    - `GET /exercicios/{id}`: Retorna um exercício específico pelo ID.
    - `POST /exercicios`: Cria um novo exercício.
    - `PUT /exercicios/{id}`: Atualiza um exercício existente pelo ID.
    - `DELETE /exercicios/{id}`: Deleta um exercício pelo ID.
    - `GET /exercicios/busca/exercicios/{nome}`: Faz uma busca dos exercicios, com base no nome.
    ### Estrutura JSON:
```bash
{
    "id": 1,
    "nome": "Supino Reto",
    "descricao": "Exercício de empurrar com barra",
    "musculos_principais": "Peitoral",
    "musculos_secundarios": "Tríceps, Ombros",
    "tipo": "Força",
    "instrucoes": "Deite-se, segure a barra e empurre para cima"
  }
```
  ### 3. 💪 Entidade Músculo
  - **Representação:** 
    - `id` (identificador único do músculo)
    - `nome` (nome do músculo, por exemplo, "Quadríceps", "Bíceps", "Peitoral")
    - `grupo_muscular` (grupo muscular ao qual pertence, por exemplo, "Perna", "Braço", "Peito")
    - `descricao` (descrição do músculo e sua função)
- **Endpoints:**
    - `GET /musculos`: Retorna uma lista de todos os músculos.
    - `GET /musculos/{id}`: Retorna um músculo específico pelo ID.
    - `POST /musculos`: Cria um novo músculo (isso pode ser mais para uma API administrativa).
    - `PUT /musculos/{id}`: Atualiza um músculo existente pelo ID (isso pode ser mais para uma API administrativa).
    - `DELETE /musculos/{id}`: Deleta um músculo pelo ID (isso pode ser mais para uma API administrativa).
    - `GET /musculos/busca/musculo/{nome}`:Faz uma busca dos musculos, com base no nome.
  ### Estrutura JSON:
```bash
{
    "id": 1,
    "nome": "Peitoral",
    "descricao": "Músculo responsável pela adução dos braços",
    "grupo_muscular": "Peito"
  }
```
## Documentação via swagger 
http://localhost:8080/q/swagger-ui/


---
title: Java Quarkus
description: A Quarkus starter app.
tags:
  - Java
  - Quarkus
---

# Quarkus Example

This is a [Quarkus](https://quarkus.io) starter app that deploys to Railway.

[![Deploy on Railway](https://railway.com/button.svg)](https://railway.com/template/orZ9Pj?referralCode=D-ZQFL)

## ✨ Features

- Java
- Quarkus
