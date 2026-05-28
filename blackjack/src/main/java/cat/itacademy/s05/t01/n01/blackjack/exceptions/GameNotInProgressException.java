package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class GameNotInProgressException extends RuntimeException{
    public GameNotInProgressException()
    {
        super("Game is not in progress");
    }
}
