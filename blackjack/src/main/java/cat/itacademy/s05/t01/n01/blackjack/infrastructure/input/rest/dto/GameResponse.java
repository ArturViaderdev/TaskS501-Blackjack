package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

import java.util.List;

public class GameResponse {
    private String id;
    private String playerName;
    private List<CardResponse> playerCards;
    private int playerScore;
    private List<CardResponse> dealerCards;
    private Integer dealerVisibleScore;
    private String status;
    private String result;

    public GameResponse() {
    }

    public GameResponse(String id,
                        String playerName,
                        List<CardResponse> playerCards,
                        int playerScore,
                        List<CardResponse> dealerCards,
                        Integer dealerVisibleScore,
                        String status,
                        String result) {
        this.id = id;
        this.playerName = playerName;
        this.playerCards = playerCards;
        this.playerScore = playerScore;
        this.dealerCards = dealerCards;
        this.dealerVisibleScore = dealerVisibleScore;
        this.status = status;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<CardResponse> getPlayerCards() {
        return playerCards;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public List<CardResponse> getDealerCards() {
        return dealerCards;
    }

    public Integer getDealerVisibleScore() {
        return dealerVisibleScore;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerCards(List<CardResponse> playerCards) {
        this.playerCards = playerCards;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setDealerCards(List<CardResponse> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public void setDealerVisibleScore(Integer dealerVisibleScore) {
        this.dealerVisibleScore = dealerVisibleScore;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
