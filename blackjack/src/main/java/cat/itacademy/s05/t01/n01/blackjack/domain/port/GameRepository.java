package cat.itacademy.s05.t01.n01.blackjack.domain.port;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface GameRepository{
    Mono<Game> save(Game game);
    Mono<Game> findById(String gameId);
}
