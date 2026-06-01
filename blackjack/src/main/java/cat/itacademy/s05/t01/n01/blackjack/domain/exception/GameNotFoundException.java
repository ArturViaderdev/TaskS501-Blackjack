package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String gameId)
    {
        super("Game not found: " + gameId);
    }

}
