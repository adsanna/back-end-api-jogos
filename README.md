# Projeto-Back-End-Api-de-Jogos
Sistema backend focado no gerenciamento de jogos e no rastreamento da experiência individual dos usuários através de múltiplas runs.

---

## Sumário

- [Equipe do Projeto](#equipe-do-projeto)
- [Visão geral](#visão-geral)
- [Solução esperada](#solução-esperada)
- [Informações complementares](#informações-complementares)
- [Regras de negócio](#regras-de-negócio)
  - [Enum de Status](#enum-de-status)
  - [GAME (Catálogo)](#game-catálogo)
  - [USER_GAME (Run)](#user_game-run)
  - [numeroRun](#numerorun)
  - [Regras por Status](#regras-por-status)
  - [Regras de Consistência](#regras-de-consistência)
  - [RATING](#rating)
- [Modelagem do banco](#modelagem-do-banco)
- [Ferramentas utilizadas](#ferramentas-utilizadas)
- [Como testar a API localmente](#como-testar-a-api-localmente)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalando e iniciando o projeto](#instalando-e-iniciando-o-projeto)
  - [Testando a API](#testando-a-api)

---

## Equipe do Projeto

| Integrante | Matrícula |
|---|---|
| Ricardo Azevedo | 01834551 |
| Francileidy Conceição | 01837744 |
| Anna Beatriz | 01849891 |
| Grazielle Diniz | 01831671 |
| Adrielly Kauany | 01835056 |

---

## Visão geral:
O sistema permite que usuários registrem jogos e acompanhem suas experiências individuais com cada título. Cada tentativa de jogar um jogo é representada por uma entidade chamada `USER_GAME` (run).

A modelagem separa:

- Dados globais do jogo (`GAME`)
- Experiência individual do usuário (`USER_GAME`)

Essa separação evita duplicação de dados, mantém o sistema escalável e permite evolução das regras de negócio sem comprometer a estrutura principal.

---

## Solução esperada:
O sistema deve permitir que usuários:

- registrem jogos em um catálogo global
- iniciem múltiplas runs de um mesmo jogo
- acompanhem status de progresso
- armazenem horas jogadas
- finalizem ou abandonem runs
- avaliem experiências concluídas

As regras de negócio devem garantir integridade dos dados e impedir inconsistências comuns em CRUDs simples.

O sistema deve manter histórico completo das experiências do usuário sem sobrescrever runs anteriores.

---

## Informações complementares:
A arquitetura do sistema foi projetada separando entidades globais de entidades relacionadas à experiência individual do usuário.

A entidade `GAME` representa exclusivamente informações universais do jogo, enquanto `USER_GAME` representa a relação do usuário com aquele título.

A complexidade do sistema está concentrada principalmente na camada de serviço, responsável por:

- validação de regras
- consistência dos status
- controle de múltiplas runs
- validação de avaliações
- proteção contra estados inválidos

Essa abordagem mantém a modelagem limpa, organizada e extensível.

---

## Regras de negócio

### Enum de Status

Os status possíveis de uma run são:

- BACKLOG
- JOGANDO
- FINALIZADO
- DROPADO

---

### GAME (Catálogo)

#### Regras:

- `nome` é obrigatório
- `genero` é opcional
- não armazena progresso do usuário

---

### USER_GAME (Run)

Representa uma tentativa concreta de jogar um jogo.

#### Campos:

- `numeroRun`
- `status`
- `horasJogadas`
- `dataInicio`
- `dataFim`

---

### numeroRun

#### Regras:

- deve ser inteiro maior ou igual a `1`
- valor padrão: `1`
- nunca pode ser nulo

#### Fluxo:

- primeira run:
  - `numeroRun = 1`

- ao rejogar:
  - criar novo `USER_GAME`
  - `numeroRun = última run + 1`

Runs anteriores nunca devem ser sobrescritas.

---

### Regras por Status

#### BACKLOG

- representa jogo ainda não iniciado
- `dataInicio` deve ser nula
- `dataFim` deve ser nula
- `horasJogadas` deve ser `0` ou nula

---

#### JOGANDO

- `dataInicio` é obrigatória
- `horasJogadas` deve ser maior ou igual a `0`
- `horasJogadas` pode ser atualizada

---

#### FINALIZADO

- `dataInicio` é obrigatória
- `dataFim` é obrigatória
- `horasJogadas` deve ser maior que `0`
- `horasJogadas` torna-se imutável

---

#### DROPADO

- `dataInicio` deve existir caso o jogo tenha sido iniciado
- `dataFim` é opcional
- `horasJogadas` deve ser maior ou igual a `0`

---

### Regras de Consistência

- um usuário não pode possuir mais de uma run `JOGANDO` para o mesmo jogo
- cada combinação (`user_id`, `game_id`, `numeroRun`) deve ser única
- `dataFim` deve ser maior ou igual a `dataInicio`

---

### RATING

#### Regras:

- `nota` deve estar entre `0` e `10`
- só pode existir caso o `USER_GAME` esteja `FINALIZADO`
- cada `USER_GAME` pode possuir apenas uma avaliação

---

## Modelagem do banco

```mermaid
erDiagram

    USER ||--o{ USER_GAME : possui
    GAME ||--o{ USER_GAME : referencia
    USER_GAME ||--|| RATING : possui

    USER {
        int id PK
        string nome
    }

    GAME {
        int id PK
        string nome
        string genero
    }

    USER_GAME {
        int id PK
        int user_id FK
        int game_id FK
        int numero_run
        enum status
        decimal horas_jogadas
        date data_inicio
        date data_fim
    }

    RATING {
        int id PK
        int nota
        int user_game_id FK
    }
````


## Ferramentas utilizadas:

| Spring                                                                                                                           | Java                                                                                                                         | Maven                                                                                                                                 | MySQL                                                                                                                                 |
| -------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| <div align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="60"/></div> | <div align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="60"/></div> | <div align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/maven/maven-original.svg" height="60"/></div> | <div align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mysql/mysql-original.svg" height="60"/></div> |
| Spring Boot 3.x                                                                                                                  | OpenJDK 21                                                                                                                   | Apache Maven 3.9.x                                                                                                                    | MySQL 8 / MariaDB                                                                                                                     |

---

## Como testar a API localmente

## Pré-requisitos

Antes de rodar a API, certifique-se de possuir instalado e configurado:

* OpenJDK 21
* Apache Maven 3.9+
* MySQL 8 ou MariaDB

> **Observação:** MariaDB é compatível com MySQL.

---

## Instalando e iniciando o projeto

### Clone o repositório

```bash
git clone https://github.com/ricardoo-azevedo/back-end-api-jogos.git
```

---

### Crie o banco de dados

Antes de iniciar a aplicação, crie manualmente um banco chamado:

```sql
CREATE DATABASE apiJogosBD;
```

Você pode criar utilizando:

- MySQL Workbench
- DBeaver
- terminal MySQL
- phpMyAdmin

---

### Configure o banco de dados

Entre na pasta:

```txt
src/main/resources
```

Abra o arquivo:

```txt
application.properties
```

Altere usuário e senha:

```properties
spring.datasource.username=usuario
spring.datasource.password=suasenha
```

---

### Rode a API

Abra o terminal na raiz do projeto e execute:

```bash
mvn spring-boot:run
```

---

## Testando a API

Após testar a aplicação, acesse a documentação Swagger:

[🔗 Testar API no Swagger](http://localhost:35555/swagger-ui/index.html#/)
