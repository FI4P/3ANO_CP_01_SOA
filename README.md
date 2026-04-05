# 🏢 Booking Room API

API REST para gerenciamento de reservas de salas corporativas — desenvolvida com **Spring Boot 3**, **Spring Security (JWT)** e **PostgreSQL**.

> **Disciplina:** Arquitetura Orientada a Serviço — FIAP 3ESPR 2026  
> **Professora:** Damiana Costa

---

## 📋 Sumário

- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Como executar](#como-executar)
- [Segurança](#segurança)
- [Endpoints](#endpoints)
- [Regras de Negócio](#regras-de-negócio)
- [Tratamento de Erros](#tratamento-de-erros)
- [Exemplos de Requisição](#exemplos-de-requisição)

---

## 🛠 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17+ |
| Spring Boot | 3.x |
| Spring Security | 6.x |
| Spring Data JPA | 3.x |
| PostgreSQL | 15+ |
| JWT (jjwt) | 0.11+ |
| Lombok | latest |
| Maven | 3.8+ |

---
## 🏗 Arquitetura
A aplicação segue uma arquitetura em camadas:

![Diagrama de Arquitetura](architecture.png)

### Estrutura de pacotes

```
src/main/java/com/fiap/soa/booking_room/
├── controller/
│   ├── AuthController.java
│   ├── BookingController.java
│   ├── EmployeeController.java
│   └── RoomController.java
├── domain/
│   ├── Booking.java
│   ├── Employee.java
│   └── Room.java
├── dto/
│   ├── error/
│   │   └── ErrorResponseDto.java
│   ├── BookingRequestDTO.java
│   ├── BookingResponseDTO.java
│   ├── RoomRequestDTO.java
│   └── RoomResponseDTO.java
├── filter/
│   └── JWTAuthenticationFilter.java
├── infrastructure/
│   └── exception/
│       ├── BadCredentialsException.java
│       ├── BusinessException.java
│       ├── DuplicatedEntityException.java
│       └── GlobalExceptionHandler.java
├── mapper/
│   ├── BookingMapper.java
│   └── RoomMapper.java
├── repository/
│   ├── BookingRepository.java
│   ├── EmployeeRepository.java
│   └── RoomRepository.java
├── security/
│   └── SecurityConfig.java
└── service/
    ├── AuthService.java
    ├── BookingService.java
    ├── EmployeeService.java
    └── RoomService.java
```

---

## ✅ Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- PostgreSQL 15+ rodando localmente (ou via Docker)
- Git

---

## 🚀 Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/booking-room.git
cd booking-room
```

### 2. Configurar o banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE booking_room;
```

### 3. Configurar variáveis de ambiente

Edite o arquivo `src/main/resources/application.properties` (ou `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/booking_room
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=sua_chave_secreta_aqui_minimo_256_bits
jwt.expiration=86400000
```

### 4. Executar a aplicação

```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### 5. (Opcional) Executar via Docker

```bash
# Subir PostgreSQL com Docker
docker run --name postgres-booking \
  -e POSTGRES_DB=booking_room \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -p 5432:5432 \
  -d postgres:15

# Executar a aplicação
mvn spring-boot:run
```

---

## 🔐 Segurança

A API utiliza **JWT (JSON Web Token)** para autenticação.

### Abordagem escolhida

Foi escolhido JWT por ser stateless — o servidor não precisa armazenar sessões, o que facilita escalabilidade. Cada token é assinado com uma chave secreta e possui tempo de expiração configurável.

### Como funciona

1. O cliente faz login em `POST /api/v1/auth/login` e recebe um token JWT.
2. Nas requisições protegidas, o token deve ser enviado no header `Authorization`:

```
Authorization: Bearer <seu_token_aqui>
```

### Rotas públicas (sem autenticação)

| Método | Rota |
|---|---|
| POST | `/api/v1/auth/register` |
| POST | `/api/v1/auth/login` |
| GET | `/api/v1/rooms/**` |
| GET | `/api/v1/bookings/**` |

### Rotas protegidas (exigem token JWT)

| Método | Rota | Motivo |
|---|---|---|
| POST | `/api/v1/bookings` | Criação de reserva |
| PATCH | `/api/v1/bookings/{id}/cancel` | Cancelamento de reserva |
| POST | `/api/v1/rooms` | Cadastro de sala |

---

## 📍 Endpoints

### Auth

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | `/api/v1/auth/register` | Registrar novo usuário | ❌ |
| POST | `/api/v1/auth/login` | Login e obtenção do token JWT | ❌ |

### Rooms (Salas)

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | `/api/v1/rooms` | Cadastrar sala | ✅ |
| GET | `/api/v1/rooms` | Listar todas as salas | ❌ |
| GET | `/api/v1/rooms/{id}` | Buscar sala por ID | ❌ |

### Bookings (Reservas)

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | `/api/v1/bookings` | Criar reserva | ✅ |
| GET | `/api/v1/bookings` | Listar todas as reservas | ❌ |
| GET | `/api/v1/bookings/{id}` | Buscar reserva por ID | ❌ |
| PATCH | `/api/v1/bookings/{id}/cancel` | Cancelar reserva | ✅ |

---

## 📜 Regras de Negócio

### Salas
- Possuem status `ATIVA` ou `INATIVA`
- Salas inativas não podem receber reservas

### Reservas
- Não é permitido conflito de horário na mesma sala (reservas `CONFIRMADA` apenas)
- O horário de fim deve ser maior que o horário de início
- Reservas canceladas **não** bloqueiam horários — o mesmo horário pode ser reservado novamente
- Criação e cancelamento exigem autenticação JWT
- Cada reserva está vinculada a um `Employee` (funcionário cadastrado)

---

## ⚠️ Tratamento de Erros

Todas as respostas de erro seguem o padrão:

```json
{
  "timestamp": "2026-04-05T20:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Já existe reserva para esta sala no horário informado",
  "path": "/api/v1/bookings"
}
```

### Tabela de erros

| Situação | Status |
|---|---|
| Dados inválidos / faltando | `400 Bad Request` |
| Token ausente ou inválido | `401 Unauthorized` |
| Acesso negado | `403 Forbidden` |
| Recurso não encontrado | `404 Not Found` |
| Conflito de horário | `409 Conflict` |
| Sala inativa / reserva já cancelada | `422 Unprocessable Entity` |
| Erro interno | `500 Internal Server Error` |

---

## 🧪 Exemplos de Requisição

### 1. Registrar usuário

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao.silva@fiap.com",
  "password": "senha123"
}
```

### 2. Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "joao.silva@fiap.com",
  "password": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 3. Cadastrar sala

```http
POST /api/v1/rooms
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "name": "Sala Apollo",
  "capacity": 10,
  "location": "Bloco A - 2º Andar",
  "status": "ATIVA"
}
```

### 4. Criar reserva

```http
POST /api/v1/bookings
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "roomId": 1,
  "employeeId": 1,
  "date": "2026-04-10",
  "startTime": "09:00",
  "endTime": "10:00",
  "purpose": "Reunião de planejamento Q2"
}
```

### 5. Cancelar reserva

```http
PATCH /api/v1/bookings/1/cancel
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### 6. Erros esperados

**Conflito de horário → 409**
```json
{
  "timestamp": "2026-04-05T20:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Já existe reserva para esta sala no horário informado",
  "path": "/api/v1/bookings"
}
```

**Sala inativa → 422**
```json
{
  "timestamp": "2026-04-05T20:00:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Não é possível reservar uma sala inativa",
  "path": "/api/v1/bookings"
}
```

---

## 🔄 Fluxo de teste sugerido

```
1. POST /auth/register       → criar usuário
2. POST /auth/login          → obter token JWT
3. POST /rooms               → criar sala ATIVA  (token obrigatório)
4. POST /rooms               → criar sala INATIVA (token obrigatório)
5. POST /bookings            → criar reserva válida (token obrigatório)
6. POST /bookings            → tentar conflito de horário ❌ 409
7. POST /bookings            → tentar sala inativa ❌ 422
8. GET  /bookings            → listar reservas
9. PATCH /bookings/1/cancel  → cancelar reserva (token obrigatório)
10. POST /bookings           → reservar mesmo horário da cancelada ✅ (deve funcionar)
```

---

## 👥 Integrantes do Grupo

| Nome | RM |
|---|---|
| Enzo Rodrigues | RM553377 |
| Rafael Cristofali | RM553521|
| Maria Julia | RM553384 |
| Hugo Santos| RM553266|

---
