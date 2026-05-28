package cat.itacademy.s05.t01.n01.blackjack.domain.port;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RankingProjectionRepository {
    Mono<Void> updateWith(GameFinished event);
    Flux<RankingItem> getRanking();
}
