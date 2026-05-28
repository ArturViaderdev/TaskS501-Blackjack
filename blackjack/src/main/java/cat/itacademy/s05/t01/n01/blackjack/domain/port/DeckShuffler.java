package cat.itacademy.s05.t01.n01.blackjack.domain.port;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.Card;

import java.util.List;

public interface DeckShuffler {
    List<Card> shuffle(List<Card> cards);
}
