package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameCreated;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.DeckIsRequiredException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameAlreadyFinishedException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotInProgressException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.PlayerNameIsRequiredException;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.*;

public class Game extends AbstractAggregateRoot<Game> {
    private final String id;
    private final String playerName;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private GameStatus status;
    private GameResult result;

    public Game(String id,
                String playerName,
                Hand playerHand,
                Hand dealerHand,
                Deck deck,
                GameStatus status,
                GameResult result) {
        if (playerName == null || playerName.isBlank()) {
            throw new PlayerNameIsRequiredException();
        }
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.playerName = playerName;
        this.playerHand = playerHand == null ? new Hand() : playerHand;
        this.dealerHand = dealerHand == null ? new Hand() : dealerHand;
        this.deck = deck;
        this.status = status == null ? GameStatus.NEW : status;
        this.result = result;
    }

    @DomainEvents
    public Collection<Object> pullDomainEvents() {
        return super.domainEvents();
    }

    @AfterDomainEventPublication
    public void clearEvents() {
        super.clearDomainEvents();
    }

    public static Game startNew(String playerName, Deck deck) {
        if (deck == null) {
            throw new DeckIsRequiredException();
        }
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        Game game = new Game(null, playerName, playerHand, dealerHand, deck, GameStatus.IN_PROGRESS, null);
        if (playerHand.isBlackjack() || dealerHand.isBlackjack()) {
            game.finishInitialBlackjackIfNeeded();
        }
        game.registerEvent(new GameCreated(game.getId(), playerName));
        return game;
    }

    public void hit() {
        ensureInProgress();
        playerHand.addCard(deck.draw());
        if (playerHand.isBust()) {
            finishGame(GameResult.DEALER_WIN);
        }
    }

    public void stand() {
        ensureInProgress();
        while (dealerMustDraw()) {
            dealerHand.addCard(deck.draw());
        }
        System.out.println("Stand");
        finishGame(resolveResult());
    }

    private boolean dealerMustDraw() {
        return dealerHand.score() < 17;
    }

    private void finishInitialBlackjackIfNeeded() {
        if (playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            finishGame(GameResult.DRAW);
            return;
        }

        if (playerHand.isBlackjack()) {
            finishGame(GameResult.PLAYER_WIN);
            return;
        }

        if (dealerHand.isBlackjack()) {
            finishGame(GameResult.DEALER_WIN);
        }
    }

    private void finishGame(GameResult result) {
        this.result = result;
        this.status = GameStatus.FINISHED;
        registerGameFinishedEvent();
    }

    private GameResult resolveResult() {
        if (dealerHand.isBust()) {
            return GameResult.PLAYER_WIN;
        }
        int playerScore = playerHand.score();
        int dealerScore = dealerHand.score();
        if (playerScore > dealerScore) {
            return GameResult.PLAYER_WIN;
        }
        if (playerScore < dealerScore) {
            return GameResult.DEALER_WIN;
        }
        return GameResult.DRAW;
    }

    private void ensureInProgress() {
        if (status == GameStatus.FINISHED) {
            throw new GameAlreadyFinishedException(id);
        }
        if (status != GameStatus.IN_PROGRESS) {
            throw new GameNotInProgressException();
        }
    }

    private void registerGameFinishedEvent() {
        registerEvent(new GameFinished(
                id,
                playerName,
                result,
                playerHand.score(),
                dealerHand.score()
        ));
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameResult getResult() {
        return result;
    }

    public List<Card> visibleDealerCards() {
        if (status == GameStatus.FINISHED) {
            return dealerHand.getCards();
        }

        if (dealerHand.getCards().isEmpty()) {
            return Collections.emptyList();
        }
        return List.of(dealerHand.getCards().get(0));
    }
}
