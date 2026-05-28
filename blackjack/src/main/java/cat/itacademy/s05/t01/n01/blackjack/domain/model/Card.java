package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.exceptions.SuitAndRankRequiredException;

import java.util.Objects;

public class Card {

    private final CardSuit suit;
    private final CardRank rank;

    public Card(CardSuit suit, CardRank rank) {
        if (suit == null || rank == null) {
            throw new SuitAndRankRequiredException();
        }
        this.suit = suit;
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public int blackjackValue() {
        return rank.getValue();
    }

    public boolean isAce() {
        return rank == CardRank.ACE;
    }

    public boolean isTenValueCard() {
        return rank == CardRank.TEN
                || rank == CardRank.JACK
                || rank == CardRank.QUEEN
                || rank == CardRank.KING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
