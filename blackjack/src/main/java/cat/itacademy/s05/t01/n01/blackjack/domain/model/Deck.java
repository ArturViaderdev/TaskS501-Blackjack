package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.exception.DeckMustContainCardsException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.NoCardsRemainingInDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> remainingCards;

    public Deck(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new DeckMustContainCardsException();
        }
        this.remainingCards = new ArrayList<>(cards);
    }

    public static Deck standard52CardDeck() {
        List<Card> cards = new ArrayList<>();

        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                cards.add(new Card(suit, rank));
            }
        }

        return new Deck(cards);
    }

    public Card draw() {
        if (remainingCards.isEmpty()) {
            throw new NoCardsRemainingInDeckException();
        }
        return remainingCards.remove(0);
    }

    public int remainingCards() {
        return remainingCards.size();
    }

    public List<Card> getRemainingCards() {
        return Collections.unmodifiableList(remainingCards);
    }
}
