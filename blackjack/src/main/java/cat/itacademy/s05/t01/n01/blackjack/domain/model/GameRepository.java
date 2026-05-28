package cat.itacademy.s05.t01.n01.blackjack.domain.model;

import java.util.Optional;

public interface GameRepository {
    Game save(Game game);
    Optional<Game> findById(String gameId);
}
