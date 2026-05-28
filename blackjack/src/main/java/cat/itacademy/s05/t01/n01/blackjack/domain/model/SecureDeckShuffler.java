package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import cat.itacademy.s05.t01.n01.blackjack.domain.port.DeckShuffler;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SecureDeckShuffler implements DeckShuffler {
    private final SecureRandom random = new SecureRandom();

    @Override
    public List<Card> shuffle(List<Card> cards) {
        List<Card> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled, random);
        return shuffled;
    }
}
