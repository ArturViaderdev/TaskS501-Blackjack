package cat.itacademy.s05.t01.n01.blackjack.domain.exception;

public class SuitAndRankRequiredException extends RuntimeException{
    public SuitAndRankRequiredException()
    {
        super("Suit and rank are required");
    }
}
