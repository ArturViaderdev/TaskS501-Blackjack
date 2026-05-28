package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class DeckMustContainCardsException extends RuntimeException{
    public DeckMustContainCardsException()
    {
        super("Deck must contain cards");
    }
}
