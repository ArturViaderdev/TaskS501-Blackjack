package cat.itacademy.s05.t01.n01.blackjack.infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpringDataPlayerRankingRepository extends ReactiveCrudRepository<PlayerRankingEntity, Long> {
    Mono<PlayerRankingEntity> findByPlayerName(String playerName);
    Flux<PlayerRankingEntity> findAllByOrderByScoreDesc();
}
