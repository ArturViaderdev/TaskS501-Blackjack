# Task S501 - BlackJack Api

# Reactive Blackjack API - Spring Boot

Reactive API for managing Blackjack games with Spring Boot WebFlux, hybrid persistence with MongoDB and MySQL, OpenAPI/Swagger documentation, and tests with JUnit and Mockito. The application follows a reactive approach with Spring WebFlux, reactive MongoDB, and reactive SQL access through Spring Data R2DBC.

## Description

This project implements a REST API for a Blackjack game with the core operations required by the assignment: create game, retrieve a game, perform plays, delete a game, retrieve the ranking, and update the player name. The web layer is built with WebFlux, API documentation is exposed with Springdoc for WebFlux, and error handling is centralized through a `GlobalExceptionHandler`.

The solution uses two databases with clearly separated responsibilities. MongoDB stores the game state and document-style game data, while MySQL stores the player ranking projection and structured performance data.

## Technologies

| Technology | Usage in the project |
|---|---|
| Spring Boot | Application base and general configuration. |
| Spring WebFlux | Reactive REST endpoint layer. |
| Spring Data Reactive MongoDB | Reactive persistence for games in MongoDB. |
| Spring Data R2DBC | Reactive persistence for relational database access. |
| MySQL / MariaDB | Ranking and relational data storage. |
| Springdoc OpenAPI | Swagger/OpenAPI generation for WebFlux. |
| JUnit 5 + Mockito | Unit tests for service and controller. |
| WebTestClient + StepVerifier | Reactive testing for controller and service. |

## Architecture

The project is organized in layers with a structure inspired by hexagonal architecture. The domain layer contains the game rules and ports, the application layer orchestrates use cases, and the infrastructure layer implements input and output adapters such as REST controllers, MongoDB repositories, MySQL adapters, and mappers.

Package structure:

```text
├── blackjack
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── cat
│       │   │       └── itacademy
│       │   │           └── s05
│       │   │               └── t01
│       │   │                   └── n01
│       │   │                       └── blackjack
│       │   │                           ├── application
│       │   │                           │   ├── dto
│       │   │                           │   │   └── query
│       │   │                           │   │       └── RankingItem.java
│       │   │                           │   └── service
│       │   │                           │       └── GameApplicationService.java
│       │   │                           ├── BlackjackApplication.java
│       │   │                           ├── domain
│       │   │                           │   ├── event
│       │   │                           │   │   ├── DomainEvent.java
│       │   │                           │   │   └── GameFinished.java
│       │   │                           │   ├── exception
│       │   │                           │   │   ├── CardCannotBeNullException.java
│       │   │                           │   │   ├── DeckIsRequiredException.java
│       │   │                           │   │   ├── DeckMustContainCardsException.java
│       │   │                           │   │   ├── GameAlreadyFinishedException.java
│       │   │                           │   │   ├── GameNotFoundException.java
│       │   │                           │   │   ├── GameNotInProgressException.java
│       │   │                           │   │   ├── InvalidGameActionException.java
│       │   │                           │   │   ├── NoCardsRemainingInDeckException.java
│       │   │                           │   │   ├── PlayerNameIsRequiredException.java
│       │   │                           │   │   └── SuitAndRankRequiredException.java
│       │   │                           │   ├── model
│       │   │                           │   │   ├── Card.java
│       │   │                           │   │   ├── CardRank.java
│       │   │                           │   │   ├── CardSuit.java
│       │   │                           │   │   ├── Deck.java
│       │   │                           │   │   ├── Game.java
│       │   │                           │   │   ├── GameResult.java
│       │   │                           │   │   ├── GameStatus.java
│       │   │                           │   │   └── Hand.java
│       │   │                           │   └── port
│       │   │                           │       ├── DeckShuffler.java
│       │   │                           │       ├── DomainEventPublisher.java
│       │   │                           │       ├── GameRepository.java
│       │   │                           │       └── RankingProjectionRepository.java
│       │   │                           └── infrastructure
│       │   │                               ├── config
│       │   │                               │   └── BeanConfig.java
│       │   │                               ├── exception
│       │   │                               │   ├── ErrorResponse.java
│       │   │                               │   └── GlobalExceptionHandler.java
│       │   │                               ├── input
│       │   │                               │   └── rest
│       │   │                               │       ├── dto
│       │   │                               │       │   ├── CardResponse.java
│       │   │                               │       │   ├── CreateGameRequest.java
│       │   │                               │       │   ├── GameResponse.java
│       │   │                               │       │   └── RankingResponse.java
│       │   │                               │       ├── GameController.java
│       │   │                               │       └── mapper
│       │   │                               │           └── RestMapper.java
│       │   │                               └── output
│       │   │                                   ├── event
│       │   │                                   │   └── SpringDomainEventPublisher.java
│       │   │                                   ├── mongo
│       │   │                                   │   ├── adapter
│       │   │                                   │   │   └── MongoGameRepositoryAdapter.java
│       │   │                                   │   ├── document
│       │   │                                   │   │   ├── CardDocument.java
│       │   │                                   │   │   └── GameDocument.java
│       │   │                                   │   ├── mapper
│       │   │                                   │   │   └── MongoGameMapper.java
│       │   │                                   │   └── repository
│       │   │                                   │       └── SpringDataGameMongoRepository.java
│       │   │                                   ├── mysql
│       │   │                                   │   ├── adapter
│       │   │                                   │   │   └── MySqlRankingProjectionAdapter.java
│       │   │                                   │   ├── entity
│       │   │                                   │   │   └── PlayerRankingEntity.java
│       │   │                                   │   ├── mapper
│       │   │                                   │   │   └── MySqlRankingMapper.java
│       │   │                                   │   └── repository
│       │   │                                   │       └── SpringDataPlayerRankingRepository.java
│       │   │                                   └── random
│       │   │                                       └── SecureDeckShuffler.java
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── cat
│                   └── itacademy
│                       └── s05
│                           └── t01
│                               └── n01
│                                   └── blackjack
│                                       ├── BlackjackApplicationTests.java
│                                       ├── GameApplicationServiceTest.java
│                                       └── GameControllerTests.java
├── production
│   ├── build
│   │   ├── mongodb
│   │   │   └── Dockerfile
│   │   └── mysql
│   │       ├── Dockerfile
│   │       ├── initialize.sql
│   │       └── mysqld.cnf
│   └── docker-compose.yml
└── README.md
```

## Game rules

Each game starts with an initial hand of two cards for the player and two cards for the dealer. While the game remains open, the player can ask for another card; if the player goes over 21, the game ends immediately with a dealer win.

When the player stands, the dealer resolves the hand using a fixed and explicit rule: the dealer draws while the score is 16 or less and stands on 17 or more; in this implementation, the dealer also stands on soft 17. Final resolution supports player win, dealer win, and draw when both valid totals are equal.

The project includes initial blackjack handling. If the player gets 21 with the first two cards and the dealer does not, the player wins; if the opposite happens, the dealer wins; and if both get an initial blackjack, the result is a draw.

## Persistence

Persistence is split into two storage systems:

- **MongoDB**: `games` collection, where the full game state is stored, including player cards, dealer cards, remaining deck, status, and result.
- **MySQL / MariaDB**: player ranking table with fields such as player name, games played, games won, and accumulated score. Spring Data R2DBC allows reactive access to this relational database.

This separation allows the game aggregate to be stored as a document while keeping an efficient and sortable ranking projection in SQL.

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/games` | Creates a new game using the player name. |
| GET | `/games/{id}` | Retrieves the state and details of a game. |
| POST | `/games/{id}/hit` | The player requests one more card. |
| POST | `/games/{id}/stand` | The player stands and the dealer resolves the hand. |
| GET | `/ranking` | Returns the player ranking. |

If the implementation needs to follow the original naming from the assignment literally, these endpoints can also be exposed as `/game/new`, `/game/{id}`, `/game/{id}/play`, `/game/{id}/delete`, `/ranking`, and `/player/{playerId}`. The current implementation can be adapted to either convention as long as it is documented consistently in Swagger and the README.

### Example: create game

**Request**

```http
POST /games
Content-Type: application/json

{
  "playerName": "Anna"
}
```

**Response 201 Created**

```json
{
  "id": "game-123",
  "playerName": "Anna",
  "playerCards": [
    { "suit": "HEARTS", "rank": "TEN" },
    { "suit": "SPADES", "rank": "SEVEN" }
  ],
  "playerScore": 17,
  "dealerCards": [
    { "suit": "CLUBS", "rank": "NINE" }
  ],
  "dealerVisibleScore": 9,
  "status": "IN_PROGRESS",
  "result": null
}
```

### Example: stand

**Request**

```http
POST /games/{id}/stand
```

**Response 200 OK**

```json
{
  "id": "game-123",
  "playerName": "Anna",
  "playerCards": [
    { "suit": "HEARTS", "rank": "TEN" },
    { "suit": "SPADES", "rank": "SEVEN" }
  ],
  "playerScore": 17,
  "dealerCards": [
    { "suit": "CLUBS", "rank": "NINE" },
    { "suit": "DIAMONDS", "rank": "EIGHT" }
  ],
  "dealerVisibleScore": 17,
  "status": "FINISHED",
  "result": "DRAW"
}
```

## Global exception handling

The application includes a `GlobalExceptionHandler` implemented with `@RestControllerAdvice`, which makes it possible to catch exceptions from annotated controllers and return homogeneous JSON responses. This mechanism is valid in Spring WebFlux for centralizing errors and simplifying the REST layer.

Recommended error mapping:

| Exception | HTTP Status |
|---|---|
| `GameNotFoundException` | `404 Not Found` |
| `PlayerNameIsRequiredException`, validations, and invalid input | `400 Bad Request` |
| `GameAlreadyFinishedException`, `GameNotInProgressException` | `409 Conflict` |
| Unhandled exceptions | `500 Internal Server Error` |

Error response format returned by the API:

```json
{
  "timestamp": "2026-06-01T08:25:10.123",
  "status": 404,
  "error": "Not Found",
  "message": "Game not found: 123",
  "path": "/games/123"
}
```

## Configuration

Example of a valid `application.properties` file for the project:

```properties
spring.application.name=blackjack-api
server.port=9000

# Reactive MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=blackjack
spring.data.mongodb.auto-index-creation=true

# Reactive MySQL / MariaDB with R2DBC
spring.r2dbc.url=r2dbc:mariadb://localhost:3306/blackjack
spring.r2dbc.username=devs
spring.r2dbc.password=pw1234

# OpenAPI / Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

For a reactive application with SQL, configuration must use `spring.r2dbc.*` and not `spring.datasource.*`, because the latter belongs to traditional JDBC. Swagger UI can be configured in Springdoc for WebFlux with the properties above.

## Running the project

### Requirements

- Java 17 or higher.
- Maven 3.9 or higher.
- MongoDB running on the configured port.
- MySQL or MariaDB running with the database already created.
- You can run docker in production directory with
```text
docker-compose up --build
```

### Start the application

```bash
cd production
java -jar blackjack-0.0.1-SNAPSHOT.jar
```

Once the application starts, Swagger/OpenAPI documentation will be available at:

- `http://localhost:9000/swagger-ui.html`
- `http://localhost:9000/api-docs`

## Testing

The assignment requires, at minimum, unit tests for at least one controller and one service using JUnit and Mockito. The proposed combination for this project is `@WebFluxTest` + `WebTestClient` for the controller, and JUnit 5 + Mockito + `StepVerifier` for the reactive service.

Recommended tests included:

- `GameControllerTest`: validates game creation, lookup by id, `hit` and `stand` actions, ranking, and error responses.
- `GameApplicationServiceTest`: validates game creation, retrieval by id, missing game errors, event publishing, and ranking retrieval.

Run tests with:

```bash
cd blackjack
mvn test
```

## Postman

It is recommended to include a Postman collection with the delivery to demonstrate the main API flow. Postman supports collection variables, chained requests, and assertions on status codes and JSON responses through test scripts.

Suggested Postman flow:

1. `POST /games` to create a game and store `gameId` in a variable.
2. `GET /games/{gameId}` to verify the initial state.
3. `POST /games/{gameId}/hit` to request one more card.
4. `POST /games/{gameId}/stand` to finish the hand.
5. `GET /ranking` to validate the ranking projection.

## Swagger / OpenAPI

Automatic API documentation is generated with Springdoc for WebFlux applications. This makes it possible to document endpoints, request bodies, and responses, and expose a navigable UI for trying the API.

Typical Maven dependency:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.8.13</version>
</dependency>
```

## Main Maven dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    </dependency>

    <dependency>
        <groupId>org.mariadb</groupId>
        <artifactId>r2dbc-mariadb</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
        <version>2.8.13</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Future improvements

- Add `DELETE /games/{id}` if an explicit delete operation should be kept to match the assignment.
- Add `PUT /player/{playerId}` if the player entity is also persisted as an independent resource.
- Add SQL migrations with Flyway or Liquibase to create the ranking table automatically.
- Add more integration tests and an exportable Postman collection.

## Purpose

Academic project developed to practice Spring Boot WebFlux, reactive persistence with MongoDB and MySQL, unit testing, API documentation, and the design of a basic Blackjack game logic.
