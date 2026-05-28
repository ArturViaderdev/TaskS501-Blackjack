package cat.itacademy.s05.t01.n01.blackjack.domain.event;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.GameResult;

import java.time.Instant;

public class GameFinished implements DomainEvent {
    private final String gameId;
    private final GameResult result;
    private final int playerScore;
    private final int dealerScore;
    private final Instant occurredAt;

    public GameFinished(String gameId, GameResult result, int playerScore, int dealerScore) {
        this.gameId = gameId;
        this.result = result;
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
        this.occurredAt = Instant.now();
    }

    public String getGameId() {
        return gameId;
    }

    public GameResult getResult() {
        return result;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    @Override
    public Instant ocurredAt() {
        return occurredAt;
    }
}
