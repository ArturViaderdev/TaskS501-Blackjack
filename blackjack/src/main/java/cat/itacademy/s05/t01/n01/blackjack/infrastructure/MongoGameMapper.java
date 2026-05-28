package cat.itacademy.s05.t01.n01.blackjack.infrastructure;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;

public class MongoGameMapper {
    private MongoGameMapper() {
    }

    public static GameDocument toDocument(Game game) {
        return new GameDocument(
                game.getId(),
                game.getPlayerHand().getCards().stream().map(MongoGameMapper::toCardDocument).toList(),
                game.getDealerHand().getCards().stream().map(MongoGameMapper::toCardDocument).toList(),
                game.getDeck().getRemainingCards().stream().map(MongoGameMapper::toCardDocument).toList(),
                game.getStatus().name(),
                game.getResult() != null ? game.getResult().name() : null
        );
    }

    public static Game toDomain(GameDocument document) {
        return new Game(
                document.getId(),
                new Hand(document.getPlayerCards().stream().map(MongoGameMapper::toCard).toList()),
                new Hand(document.getDealerCards().stream().map(MongoGameMapper::toCard).toList()),
                new Deck(document.getRemainingDeck().stream().map(MongoGameMapper::toCard).toList()),
                GameStatus.valueOf(document.getStatus()),
                document.getResult() != null ? GameResult.valueOf(document.getResult()) : null
        );
    }

    private static CardDocument toCardDocument(Card card) {
        return new CardDocument(card.getSuit().name(), card.getRank().name());
    }

    private static Card toCard(CardDocument document) {
        return new Card(
                CardSuit.valueOf(document.getSuit()),
                CardRank.valueOf(document.getRank())
        );
    }
}
