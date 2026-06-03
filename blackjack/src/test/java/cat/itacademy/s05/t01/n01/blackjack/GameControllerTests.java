package cat.itacademy.s05.t01.n01.blackjack;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.query.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.application.service.GameApplicationService;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.exception.GlobalExceptionHandler;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.GameController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;

@WebFluxTest(controllers = GameController.class)
@Import(GlobalExceptionHandler.class)
public class GameControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GameApplicationService gameApplicationService;

    @Test
    @DisplayName("POST /games should create a game and return 201")
    void shouldCreateGame() {
        Game game = buildInProgressGame("game-1", "Anna");
        Mockito.when(gameApplicationService.createGame("Anna"))
                .thenReturn(Mono.just(game));
        webTestClient.post()
                .uri("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "playerName": "Anna"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("game-1")
                .jsonPath("$.playerName").isEqualTo("Anna")
                .jsonPath("$.status").isEqualTo("IN_PROGRESS")
                .jsonPath("$.playerCards").isArray()
                .jsonPath("$.dealerCards").isArray();
    }

    @Test
    @DisplayName("GET /games/{id} should return a game")
    void shouldGetGameById() {
        Game game = buildInProgressGame("game-1", "Anna");
        Mockito.when(gameApplicationService.getGame("game-1"))
                .thenReturn(Mono.just(game));
        webTestClient.get()
                .uri("/games/game-1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("game-1")
                .jsonPath("$.playerName").isEqualTo("Anna")
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }

    @Test
    @DisplayName("POST /games/{id}/hit should return updated game")
    void shouldHitGame() {
        Game game = buildFinishedPlayerBustGame("game-1", "Anna");
        Mockito.when(gameApplicationService.hit("game-1"))
                .thenReturn(Mono.just(game));
        webTestClient.post()
                .uri("/games/game-1/hit")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("game-1")
                .jsonPath("$.status").isEqualTo("FINISHED")
                .jsonPath("$.result").isEqualTo("DEALER_WIN");
    }

    @Test
    @DisplayName("POST /games/{id}/stand should finish game")
    void shouldStandGame() {
        Game game = buildFinishedPlayerWinGame("game-1", "Anna");
        Mockito.when(gameApplicationService.stand("game-1"))
                .thenReturn(Mono.just(game));
        webTestClient.post()
                .uri("/games/game-1/stand")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("game-1")
                .jsonPath("$.status").isEqualTo("FINISHED")
                .jsonPath("$.result").isEqualTo("PLAYER_WIN");
    }

    @Test
    @DisplayName("GET /ranking should return ranking list")
    void shouldReturnRanking() {
        RankingItem anna = new RankingItem("Anna", 3, 2, 7);
        RankingItem marc = new RankingItem("Marc", 4, 1, 4);
        Mockito.when(gameApplicationService.getRanking())
                .thenReturn(Flux.just(anna, marc));
        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].playerName").isEqualTo("Anna")
                .jsonPath("$[0].gamesPlayed").isEqualTo(3)
                .jsonPath("$[0].gamesWon").isEqualTo(2)
                .jsonPath("$[0].score").isEqualTo(7)
                .jsonPath("$[1].playerName").isEqualTo("Marc");
    }

    @Test
    @DisplayName("GET /games/{id} should return 404 when game does not exist")
    void shouldReturnNotFoundWhenGameDoesNotExist() {
        Mockito.when(gameApplicationService.getGame("missing-id"))
                .thenReturn(Mono.error(new GameNotFoundException("missing-id")));
        webTestClient.get()
                .uri("/games/missing-id")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.message").exists()
                .jsonPath("$.path").isEqualTo("/games/missing-id");
    }

    @Test
    @DisplayName("POST /games should return 400 when playerName is missing")
    void shouldReturnBadRequestWhenPlayerNameIsMissing() {
        Mockito.when(gameApplicationService.createGame(null))
                .thenReturn(Mono.error(new IllegalArgumentException("Player name is required")));
        webTestClient.post()
                .uri("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.error").isEqualTo("Bad Request");
    }

    private Game buildInProgressGame(String id, String playerName) {
        Hand playerHand = new Hand(List.of(
                new Card(CardSuit.HEARTS, CardRank.TEN),
                new Card(CardSuit.SPADES, CardRank.SEVEN)
        ));

        Hand dealerHand = new Hand(List.of(
                new Card(CardSuit.CLUBS, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.SIX)
        ));

        Deck deck = new Deck(List.of(
                new Card(CardSuit.HEARTS, CardRank.TWO),
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.CLUBS, CardRank.FOUR)
        ));
        return new Game(id, playerName, playerHand, dealerHand, deck, GameStatus.IN_PROGRESS, null);
    }

    private Game buildFinishedPlayerBustGame(String id, String playerName) {
        Hand playerHand = new Hand(List.of(
                new Card(CardSuit.HEARTS, CardRank.TEN),
                new Card(CardSuit.SPADES, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.FIVE)
        ));
        Hand dealerHand = new Hand(List.of(
                new Card(CardSuit.DIAMONDS, CardRank.EIGHT),
                new Card(CardSuit.CLUBS, CardRank.SEVEN)
        ));
        Deck deck = new Deck(List.of(
                new Card(CardSuit.HEARTS, CardRank.THREE)
        ));
        return new Game(id, playerName, playerHand, dealerHand, deck, GameStatus.FINISHED, GameResult.DEALER_WIN);
    }

    private Game buildFinishedPlayerWinGame(String id, String playerName) {
        Hand playerHand = new Hand(List.of(
                new Card(CardSuit.HEARTS, CardRank.TEN),
                new Card(CardSuit.SPADES, CardRank.NINE)
        ));
        Hand dealerHand = new Hand(List.of(
                new Card(CardSuit.DIAMONDS, CardRank.EIGHT),
                new Card(CardSuit.CLUBS, CardRank.SEVEN),
                new Card(CardSuit.HEARTS, CardRank.NINE)
        ));
        Deck deck = new Deck(List.of(
                new Card(CardSuit.SPADES, CardRank.TWO)
        ));
        return new Game(id, playerName, playerHand, dealerHand, deck, GameStatus.FINISHED, GameResult.PLAYER_WIN);
    }
}
