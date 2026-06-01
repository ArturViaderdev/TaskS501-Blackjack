package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.adapter;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.Game;

import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.mapper.MongoGameMapper;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.repository.SpringDataGameMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
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
