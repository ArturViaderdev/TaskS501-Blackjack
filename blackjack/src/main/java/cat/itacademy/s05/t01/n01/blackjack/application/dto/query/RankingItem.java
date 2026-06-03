package cat.itacademy.s05.t01.n01.blackjack.application.dto.query;

public record RankingItem(
        String playerName,
        int gamesPlayed,
        int gamesWon,
        int score
) {}
