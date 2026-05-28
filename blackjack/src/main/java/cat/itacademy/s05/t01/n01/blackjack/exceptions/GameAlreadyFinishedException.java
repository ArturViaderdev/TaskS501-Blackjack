package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class GameAlreadyFinishedException extends RuntimeException{
    public GameAlreadyFinishedException(String gameId)
    {
        super("Game already finished: " + gameId);
    }
}
