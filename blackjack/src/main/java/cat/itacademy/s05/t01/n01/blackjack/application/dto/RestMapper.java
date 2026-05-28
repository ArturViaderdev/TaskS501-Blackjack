package cat.itacademy.s05.t01.n01.blackjack.application.dto;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.Hand;

import java.util.List;

public final class RestMapper {
    private RestMapper() {
    }

    public static GameResponse toResponse(Game game) {
        List<CardResponse> visibleDealerCards = game.visibleDealerCards()
                .stream()
                .map(RestMapper::toCardResponse)
                .toList();

        Integer dealerVisibleScore = visibleDealerCards.isEmpty()
                ? null
                : new Hand(
                game.visibleDealerCards()
        ).score();

        return new GameResponse(
                game.getId(),
                game.getPlayerName(),
                game.getPlayerHand().getCards().stream().map(RestMapper::toCardResponse).toList(),
                game.getPlayerHand().score(),
                visibleDealerCards,
                dealerVisibleScore,
                game.getStatus().name(),
                game.getResult() != null ? game.getResult().name() : null
        );
    }

    public static RankingResponse toResponse(RankingItem item) {
        return new RankingResponse(
                item.getPlayerName(),
                item.getGamesPlayed(),
                item.getGamesWon(),
                item.getScore()
        );
    }

    private static CardResponse toCardResponse(Card card) {
        return new CardResponse(card.getSuit().name(), card.getRank().name());
    }
}
