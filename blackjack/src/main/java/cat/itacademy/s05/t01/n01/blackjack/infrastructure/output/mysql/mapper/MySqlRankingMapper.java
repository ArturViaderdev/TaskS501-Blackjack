package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.mapper;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.query.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mysql.entity.PlayerRankingEntity;

public final class MySqlRankingMapper {
    private MySqlRankingMapper() {
    }

    public static RankingItem toDto(PlayerRankingEntity entity) {
        return new RankingItem(
                entity.getPlayerName(),
                entity.getGamesPlayed(),
                entity.getGamesWon(),
                entity.getScore()
        );
    }
}
