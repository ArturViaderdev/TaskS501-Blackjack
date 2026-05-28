package cat.itacademy.s05.t01.n01.blackjack.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant ocurredAt();
}
