package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import java.util.List;

public interface DeckShuffler {
    List<Card> shuffle(List<Card> cards);
}
