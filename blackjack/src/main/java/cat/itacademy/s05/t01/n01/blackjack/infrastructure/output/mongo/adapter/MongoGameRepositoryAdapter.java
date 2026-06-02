package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.adapter;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.Game;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.mapper.MongoGameMapper;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.repository.SpringDataGameMongoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class MongoGameRepositoryAdapter implements GameRepository {
    private final SpringDataGameMongoRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public MongoGameRepositoryAdapter(SpringDataGameMongoRepository repository,
                                      ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<Game> save(Game game) {
        return repository.save(MongoGameMapper.toDocument(game))
                .then(Mono.fromRunnable(() -> {
                    if (game.getResult() != null && game.getStatus() == GameStatus.FINISHED) {
                        eventPublisher.publishEvent(new GameFinished(
                                game.getId(),
                                game.getPlayerName(),
                                game.getResult(),
                                game.getPlayerHand().score(),
                                game.getDealerHand().score()
                        ));

                    }
                }))
                .thenReturn(game);
    }

    @Override
    public Mono<Game> findById(String gameId) {
        return repository.findById(gameId)
                .map(MongoGameMapper::toDomain);
    }
}