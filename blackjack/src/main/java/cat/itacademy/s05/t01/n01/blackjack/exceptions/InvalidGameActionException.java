package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class InvalidGameActionException extends RuntimeException{
    public InvalidGameActionException(String message)
    {
        super(message);
    }
}
