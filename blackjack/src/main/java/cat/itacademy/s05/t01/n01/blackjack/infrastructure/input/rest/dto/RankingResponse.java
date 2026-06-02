package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

public record RankingResponse(
        String playerName,
        int gamesPlayed,
        int gamesWon,
        int score
) {
}
