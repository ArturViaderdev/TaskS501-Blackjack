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
                .map(existing -> {
                    int gamesPlayed = existing.getGamesPlayed() == null ? 0 : existing.getGamesPlayed();
                    int gamesWon = existing.getGamesWon() == null ? 0 : existing.getGamesWon();
                    int score = existing.getScore() == null ? 0 : existing.getScore();

                    existing.setGamesPlayed(gamesPlayed + 1);

                    if (event.getResult() == GameResult.PLAYER_WIN) {
                        existing.setGamesWon(gamesWon + 1);
                        existing.setScore(score + 3);
                    } else if (event.getResult() == GameResult.DRAW) {
                        existing.setScore(score + 1);
                    } else {
                        existing.setScore(score);
                    }

                    return existing;
                })
                .flatMap(repository::save)
                .then();
    }

    @Override
    public Flux<RankingItem> getRanking() {
        return repository.findAllByOrderByScoreDesc()
                .map(MySqlRankingMapper::toDto);
    }
}
