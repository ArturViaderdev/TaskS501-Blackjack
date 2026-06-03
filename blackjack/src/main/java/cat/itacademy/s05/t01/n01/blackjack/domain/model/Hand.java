package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.exception.CardCannotBeNullException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public Hand() {
    }

    public Hand(List<Card> initialCards) {
        if (initialCards != null) {
            this.cards.addAll(initialCards);
        }
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new CardCannotBeNullException();
        }
        cards.add(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int score() {
        int total = 0;
        int aces = 0;
        for (Card card : cards) {
            total += card.blackjackValue();
            if (card.isAce()) {
                aces++;
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }

    public boolean isBust() {
        return score() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && score() == 21;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public boolean isSoft() {
        int total = 0;
        int aces = 0;
        for (Card card : cards) {
            total += card.blackjackValue();
            if (card.isAce()) {
                aces++;
            }
        }
        return aces > 0 && total <= 21;
    }
}
