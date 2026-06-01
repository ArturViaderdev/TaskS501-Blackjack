package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "games")
public class GameDocument {
    @Id
    private String id;
    private String playerName;
    private List<CardDocument> playerCards;
    private List<CardDocument> dealerCards;
    private List<CardDocument> remainingDeck;
    private String status;
    private String result;

    public GameDocument() {
    }

    public GameDocument(String id,
                        String playerName,
                        List<CardDocument> playerCards,
                        List<CardDocument> dealerCards,
                        List<CardDocument> remainingDeck,
                        String status,
                        String result) {
        this.id = id;
        this.playerName = playerName;
        this.playerCards = playerCards;
        this.dealerCards = dealerCards;
        this.remainingDeck = remainingDeck;
        this.status = status;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<CardDocument> getPlayerCards() {
        return playerCards;
    }

    public List<CardDocument> getDealerCards() {
        return dealerCards;
    }

    public List<CardDocument> getRemainingDeck() {
        return remainingDeck;
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

    public void setPlayerCards(List<CardDocument> playerCards) {
        this.playerCards = playerCards;
    }

    public void setDealerCards(List<CardDocument> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public void setRemainingDeck(List<CardDocument> remainingDeck) {
        this.remainingDeck = remainingDeck;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
