package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

public class CreateGameRequest {
    private String playerName;

    public CreateGameRequest() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
