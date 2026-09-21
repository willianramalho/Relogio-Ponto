# PontoFlex

Sistema de controle de ponto eletronico e gestao de jornada de trabalho (CLT), com
registro de ponto via geolocalizacao, calculo automatico de horas extras, banco de
horas, adicional noturno e emissao de relatorios (espelho de ponto).

Este e um projeto de portfolio inspirado em modulos de RH/Ponto de ERPs corporativos
(TOTVS, SAP, Senior). O documento completo de arquitetura e design (visao geral,
regras de negocio, modelo de dados, API, roadmap) esta no Claude Doc gerado junto com
este esqueleto — copie o conteudo para `docs/system-design.md` se preferir mante-lo
versionado no repositorio.

## Stack

- Java 25 + Spring Boot 3.3
- PostgreSQL 16 + Flyway
- Spring Security (JWT)
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito + Testcontainers
- Docker Compose
- GitHub Actions (CI)

## Como rodar localmente

```bash
docker compose up -d postgres
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. Documentacao interativa em
`http://localhost:8080/swagger-ui.html`.

Para rodar tudo em containers (app + banco):

```bash
docker compose up --build
```

## Rodando os testes

```bash
./mvnw test          # unitarios (dominio, services)
./mvnw verify         # inclui testes de integracao com Testcontainers
```

## Estrutura do projeto

```
src/main/java/com/pontoflex/
├── domain/            # regras de negocio puras (sem Spring)
├── application/        # services e DTOs, orquestram o dominio
├── infrastructure/     # JPA, seguranca, relatorios, config
└── web/                # controllers REST
```

Veja a Secao 7 do documento de system design para o racional completo dessa separacao
e a alternativa mais simples (entidades JPA como dominio), caso prefira comecar por
um caminho mais direto.

## Roadmap sugerido

1. Fundacao (setup, Docker, Flyway, health-check) — **feito neste esqueleto**
2. Cadastros base (Colaborador, LocalTrabalho, EscalaTrabalho) + autenticacao JWT
3. Registro de ponto + validacao de geofence — **GeofenceValidator ja incluido como exemplo**
4. Motor de calculo de jornada (horas extras, adicional noturno)
5. Banco de horas (ledger de lancamentos)
6. Feriados
7. Relatorio (espelho de ponto em PDF/CSV)
8. Polimento (exception handler, Swagger, README final, dados de exemplo)

Peca ao Claude Code, no VS Code, para implementar uma fase de cada vez, mantendo
commits pequenos e revisaveis.

## Licenca

Projeto de portfolio pessoal — sem licenca de uso restrito definida.
