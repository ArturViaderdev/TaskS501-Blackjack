package cat.itacademy.s05.t01.n01.blackjack.domain.event;

import java.util.List;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}
