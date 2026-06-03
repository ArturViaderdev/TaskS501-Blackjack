package cat.itacademy.s05.t01.n01.blackjack;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.query.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.application.service.GameApplicationService;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DeckShuffler;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameApplicationServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RankingProjectionRepository rankingProjectionRepository;

    @Mock
    private DeckShuffler deckShuffler;

    @InjectMocks
    private GameApplicationService gameApplicationService;

    @Test
    @DisplayName("createGame should create and save a new game")
    void shouldCreateGame() {
        List<Card> shuffledCards = buildShuffledDeckForNewGame();
        when(deckShuffler.shuffle(anyList())).thenReturn(shuffledCards);
        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        StepVerifier.create(gameApplicationService.createGame("Anna"))
                .assertNext(game -> {
                    assertEquals("Anna", game.getPlayerName());
                    assertNotNull(game.getPlayerHand());
                    assertNotNull(game.getDealerHand());
                    assertTrue(game.getPlayerHand().getCards().size() >= 2);
                    assertTrue(game.getDealerHand().getCards().size() >= 2);
                    assertTrue(game.getStatus() == GameStatus.IN_PROGRESS
                            || game.getStatus() == GameStatus.FINISHED);
                })
                .verifyComplete();
        verify(deckShuffler, times(1)).shuffle(anyList());
        verify(gameRepository, times(1)).save(any(Game.class));
        verifyNoMoreInteractions(deckShuffler, gameRepository);
    }

    @Test
    @DisplayName("getGame should return game when it exists")
    void shouldGetGameById() {
        Game game = buildInProgressGame("game-1", "Anna");
        when(gameRepository.findById("game-1")).thenReturn(Mono.just(game));
        StepVerifier.create(gameApplicationService.getGame("game-1"))
                .assertNext(found -> {
                    assertEquals("game-1", found.getId());
                    assertEquals("Anna", found.getPlayerName());
                })
                .verifyComplete();
        verify(gameRepository, times(1)).findById("game-1");
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("getGame should return error when game does not exist")
    void shouldReturnErrorWhenGameNotFound() {
        when(gameRepository.findById("missing-id")).thenReturn(Mono.empty());
        StepVerifier.create(gameApplicationService.getGame("missing-id"))
                .expectError(GameNotFoundException.class)
                .verify();
        verify(gameRepository, times(1)).findById("missing-id");
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("hit should update game and save it")
    void shouldHitGame() {
        Game game = buildInProgressGame("game-1", "Anna");
        when(gameRepository.findById("game-1")).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        StepVerifier.create(gameApplicationService.hit("game-1"))
                .assertNext(updated -> {
                    assertEquals("game-1", updated.getId());
                    assertTrue(updated.getPlayerHand().getCards().size() >= 3);
                })
                .verifyComplete();
        verify(gameRepository, times(1)).findById("game-1");
        verify(gameRepository, times(1)).save(any(Game.class));
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("stand should finish game and save it")
    void shouldStandGame() {
        Game game = buildInProgressGame("game-1", "Anna");
        when(gameRepository.findById("game-1")).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        StepVerifier.create(gameApplicationService.stand("game-1"))
                .assertNext(updated -> {
                    assertEquals("game-1", updated.getId());
                    assertEquals(GameStatus.FINISHED, updated.getStatus());
                    assertTrue(updated.getResult() == GameResult.PLAYER_WIN
                            || updated.getResult() == GameResult.DEALER_WIN
                            || updated.getResult() == GameResult.DRAW);
                })
                .verifyComplete();
        verify(gameRepository, times(1)).findById("game-1");
        verify(gameRepository, times(1)).save(any(Game.class));
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("getRanking should return ranking ordered from repository")
    void shouldReturnRanking() {
        RankingItem anna = new RankingItem("Anna", 5, 3, 10);
        RankingItem marc = new RankingItem("Marc", 4, 2, 7);
        when(rankingProjectionRepository.getRanking())
                .thenReturn(Flux.just(anna, marc));
        StepVerifier.create(gameApplicationService.getRanking())
                .expectNext(anna)
                .expectNext(marc)
                .verifyComplete();
        verify(rankingProjectionRepository, times(1)).getRanking();
        verifyNoMoreInteractions(rankingProjectionRepository);
    }

    private Game buildInProgressGame(String id, String playerName) {
        Hand playerHand = new Hand(List.of(
                new Card(CardSuit.HEARTS, CardRank.TEN),
                new Card(CardSuit.SPADES, CardRank.SIX)
        ));
        Hand dealerHand = new Hand(List.of(
                new Card(CardSuit.CLUBS, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        ));
        Deck deck = new Deck(List.of(
                new Card(CardSuit.HEARTS, CardRank.FIVE),
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.CLUBS, CardRank.TWO),
                new Card(CardSuit.DIAMONDS, CardRank.FOUR)
        ));
        return new Game(id, playerName, playerHand, dealerHand, deck, GameStatus.IN_PROGRESS, null);
    }

    private List<Card> buildShuffledDeckForNewGame() {
        return List.of(
                new Card(CardSuit.HEARTS, CardRank.TEN),
                new Card(CardSuit.CLUBS, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.SIX),
                new Card(CardSuit.HEARTS, CardRank.FIVE),
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.CLUBS, CardRank.TWO),
                new Card(CardSuit.DIAMONDS, CardRank.FOUR)
        );
    }
}
