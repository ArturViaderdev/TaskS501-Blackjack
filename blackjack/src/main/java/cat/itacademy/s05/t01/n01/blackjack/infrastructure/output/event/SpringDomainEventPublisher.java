package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.event;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.DomainEvent;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DomainEventPublisher;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final RankingProjectionRepository rankingProjectionRepository;

    public SpringDomainEventPublisher(RankingProjectionRepository rankingProjectionRepository) {
        this.rankingProjectionRepository = rankingProjectionRepository;
    }

    @Override
    public Mono<Void> publish(List<DomainEvent> events) {
        return Flux.fromIterable(events)
                .flatMap(this::dispatch)
                .then();
    }

    private Mono<Void> dispatch(DomainEvent event) {
        if (event instanceof GameFinished gameFinished) {
            return rankingProjectionRepository.updateWith(gameFinished);
        }
        return Mono.empty();
    }
}
