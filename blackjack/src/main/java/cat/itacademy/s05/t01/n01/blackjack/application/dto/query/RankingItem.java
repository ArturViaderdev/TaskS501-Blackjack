package cat.itacademy.s05.t01.n01.blackjack.application.dto.query;

public class RankingItem {
    private final String playerName;
    private final int gamesPlayed;
    private final int gamesWon;
    private final int score;

    public RankingItem(String playerName, int gamesPlayed, int gamesWon, int score) {
        this.playerName = playerName;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getScore() {
        return score;
    }
}
