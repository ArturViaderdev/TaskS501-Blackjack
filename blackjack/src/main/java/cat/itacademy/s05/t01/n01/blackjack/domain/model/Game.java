package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.DomainEvent;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.exceptions.DeckIsRequiredException;
import cat.itacademy.s05.t01.n01.blackjack.exceptions.GameAlreadyFinishedException;
import cat.itacademy.s05.t01.n01.blackjack.exceptions.GameNotInProgressException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Game {
    private final String id;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private GameStatus status;
    private GameResult result;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Game(String id, Hand playerHand, Hand dealerHand, Deck deck, GameStatus status, GameResult result) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.playerHand = playerHand == null ? new Hand() : playerHand;
        this.dealerHand = dealerHand == null ? new Hand() : dealerHand;
        this.deck = deck;
        this.status = status == null ? GameStatus.NEW : status;
        this.result = result;
    }

    public static Game startNew(Deck deck) {
        if (deck == null) {
            throw new DeckIsRequiredException();
        }

        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());

        Game game = new Game(null, playerHand, dealerHand, deck, GameStatus.IN_PROGRESS, null);

        if (playerHand.isBlackjack() || dealerHand.isBlackjack()) {
            game.finishInitialBlackjackIfNeeded();
        }

        return game;
    }

    public void hit() {
        ensureInProgress();

        playerHand.addCard(deck.draw());

        if (playerHand.isBust()) {
            status = GameStatus.FINISHED;
            result = GameResult.DEALER_WIN;
            registerGameFinishedEvent();
        }
    }

    public void stand() {
        ensureInProgress();

        while (dealerMustDraw()) {
            dealerHand.addCard(deck.draw());
        }

        result = resolveResult();
        status = GameStatus.FINISHED;
        registerGameFinishedEvent();
    }

    private boolean dealerMustDraw() {
        return dealerHand.score() < 17;
    }

    private void finishInitialBlackjackIfNeeded() {
        if (playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            result = GameResult.DRAW;
            status = GameStatus.FINISHED;
            registerGameFinishedEvent();
            return;
        }

        if (playerHand.isBlackjack()) {
            result = GameResult.PLAYER_WIN;
            status = GameStatus.FINISHED;
            registerGameFinishedEvent();
            return;
        }

        if (dealerHand.isBlackjack()) {
            result = GameResult.DEALER_WIN;
            status = GameStatus.FINISHED;
            registerGameFinishedEvent();
        }
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
        domainEvents.add(new GameFinished(
                id,
                result,
                playerHand.score(),
                dealerHand.score()
        ));
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    public String getId() {
        return id;
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
