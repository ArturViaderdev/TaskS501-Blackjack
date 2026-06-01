package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class PlayerNameIsRequiredException extends RuntimeException{
    public PlayerNameIsRequiredException()
    {
        super("Player name is required");
    }
}
