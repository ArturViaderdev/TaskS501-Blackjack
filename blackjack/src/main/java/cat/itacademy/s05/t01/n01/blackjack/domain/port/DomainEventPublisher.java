package cat.itacademy.s05.t01.n01.blackjack.domain.port;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.DomainEvent;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DomainEventPublisher {
    Mono<Void> publish(List<DomainEvent> events);
}
