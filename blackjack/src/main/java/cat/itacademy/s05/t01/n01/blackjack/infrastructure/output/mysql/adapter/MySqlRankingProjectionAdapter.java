package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.adapter;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.query.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.GameResult;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.entity.PlayerRankingEntity;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.mapper.MySqlRankingMapper;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.repository.SpringDataPlayerRankingRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MySqlRankingProjectionAdapter implements RankingProjectionRepository {
    private final SpringDataPlayerRankingRepository repository;

    public MySqlRankingProjectionAdapter(SpringDataPlayerRankingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> updateWith(GameFinished event) {
        String playerName = event.getPlayerName();

        return repository.findByPlayerName(playerName)
                .defaultIfEmpty(new PlayerRankingEntity(null, playerName, 0, 0, 0))
                .flatMap(existing -> {
                    existing.setGamesPlayed(existing.getGamesPlayed() + 1);

                    if (event.getResult() == GameResult.PLAYER_WIN) {
                        existing.setGamesWon(existing.getGamesWon() + 1);
                        existing.setScore(existing.getScore() + 3);
                    } else if (event.getResult() == GameResult.DRAW) {
                        existing.setScore(existing.getScore() + 1);
                    }

                    return repository.save(existing);
                })
                .then();
    }

    @Override
    public Flux<RankingItem> getRanking() {
        return repository.findAllByOrderByScoreDesc()
                .map(MySqlRankingMapper::toDto);
    }
}
