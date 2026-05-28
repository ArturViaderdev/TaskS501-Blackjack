package cat.itacademy.s05.t01.n01.blackjack.infrastructure;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.Game;

import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import reactor.core.publisher.Mono;

public class MongoGameRepositoryAdapter implements GameRepository {
    private final SpringDataGameMongoRepository repository;

    public MongoGameRepositoryAdapter(SpringDataGameMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Game> save(Game game) {
        return repository.save(MongoGameMapper.toDocument(game))
                .map(MongoGameMapper::toDomain);
    }

    @Override
    public Mono<Game> findById(String gameId) {
        return repository.findById(gameId)
                .map(MongoGameMapper::toDomain);
    }
}
