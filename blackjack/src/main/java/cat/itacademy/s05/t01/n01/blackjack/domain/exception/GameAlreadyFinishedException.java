package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class GameAlreadyFinishedException extends RuntimeException{
    public GameAlreadyFinishedException(String gameId)
    {
        super("Game already finished: " + gameId);
    }
}
