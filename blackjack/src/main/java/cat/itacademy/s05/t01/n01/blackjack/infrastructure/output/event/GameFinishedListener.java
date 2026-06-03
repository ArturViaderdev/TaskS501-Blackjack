package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.event;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameFinishedListener {

    private final RankingProjectionRepository rankingProjectionRepository;

    public GameFinishedListener(RankingProjectionRepository rankingProjectionRepository) {
        this.rankingProjectionRepository = rankingProjectionRepository;
    }

    @EventListener
    public void on(GameFinished event) {
        rankingProjectionRepository.updateWith(event).subscribe();
    }
}