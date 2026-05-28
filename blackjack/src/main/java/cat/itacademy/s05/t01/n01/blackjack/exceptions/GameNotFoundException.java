package cat.itacademy.s05.t01.n01.blackjack.exceptions;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String gameId)
    {
        super("Game not found: " + gameId);
    }

}
