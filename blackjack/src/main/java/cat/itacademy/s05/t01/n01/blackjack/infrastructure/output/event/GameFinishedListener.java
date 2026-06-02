package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.event;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class GameFinishedListener {

    private final RankingProjectionRepository rankingProjectionRepository;

    public GameFinishedListener(RankingProjectionRepository rankingProjectionRepository) {
        this.rankingProjectionRepository = rankingProjectionRepository;
    }

    @EventListener
    public void on(GameFinished event) {
        rankingProjectionRepository.updateWith(event)
                .subscribe();
    }
}