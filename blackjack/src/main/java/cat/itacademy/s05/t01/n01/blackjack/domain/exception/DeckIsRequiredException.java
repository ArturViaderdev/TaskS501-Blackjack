package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class DeckIsRequiredException extends RuntimeException{
    public DeckIsRequiredException()
    {
        super("Deck is required");
    }
}
