package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

public class CardResponse {
    private String suit;
    private String rank;

    public CardResponse() {
    }

    public CardResponse(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
