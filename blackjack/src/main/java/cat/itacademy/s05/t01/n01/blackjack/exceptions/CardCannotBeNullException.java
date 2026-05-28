package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class CardCannotBeNullException extends RuntimeException{
    public CardCannotBeNullException()
    {
        super("Card cannot be null");
    }
}
