package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.exception.SuitAndRankRequiredException;

public record Card(CardSuit suit, CardRank rank) {

    public Card {
        if (suit == null || rank == null) {
            throw new SuitAndRankRequiredException();
        }
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
    public String toString() {
        return rank + " of " + suit;
    }
}
