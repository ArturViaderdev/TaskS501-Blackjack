package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class NoCardsRemainingInDeckException extends RuntimeException{
    public NoCardsRemainingInDeckException()
    {
        super("No cards remaining in deck");
    }
}
