package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

public class RankingResponse {
    private String playerName;
    private int gamesPlayed;
    private int gamesWon;
    private int score;

    public RankingResponse() {
    }

    public RankingResponse(String playerName, int gamesPlayed, int gamesWon, int score) {
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
