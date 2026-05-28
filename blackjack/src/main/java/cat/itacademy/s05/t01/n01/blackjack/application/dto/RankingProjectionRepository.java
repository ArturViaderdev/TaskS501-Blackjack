package cat.itacademy.s05.t01.n01.blackjack.application.dto;

import cat.itacademy.s05.t01.n01.blackjack.domain.event.GameFinished;

import java.util.List;

public interface RankingProjectionRepository {
    void updateWith(GameFinished event);
    List<RankingItem> getRanking();
}
