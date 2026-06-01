package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.document.CardDocument;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.document.GameDocument;

import java.util.Collections;
import java.util.List;

public final class MongoGameMapper {
    private MongoGameMapper() {
    }

    public static GameDocument toDocument(Game game) {
        if (game == null) {
            return null;
        }

        return new GameDocument(
                game.getId(),
                game.getPlayerName(),
                toCardDocuments(game.getPlayerHand().getCards()),
                toCardDocuments(game.getDealerHand().getCards()),
                toCardDocuments(game.getDeck().getRemainingCards()),
                game.getStatus().name(),
                game.getResult() != null ? game.getResult().name() : null
        );
    }

    public static Game toDomain(GameDocument document) {
        if (document == null) {
            return null;
        }

        return new Game(
                document.getId(),
                document.getPlayerName(),
                new Hand(toCards(document.getPlayerCards())),
                new Hand(toCards(document.getDealerCards())),
                new Deck(toCards(document.getRemainingDeck())),
                GameStatus.valueOf(document.getStatus()),
                document.getResult() != null ? GameResult.valueOf(document.getResult()) : null
        );
    }

    private static List<CardDocument> toCardDocuments(List<Card> cards) {
        if (cards == null) {
            return Collections.emptyList();
        }

        return cards.stream()
                .map(MongoGameMapper::toCardDocument)
                .toList();
    }

    private static List<Card> toCards(List<CardDocument> cardDocuments) {
        if (cardDocuments == null) {
            return Collections.emptyList();
        }

        return cardDocuments.stream()
                .map(MongoGameMapper::toCard)
                .toList();
    }

    private static CardDocument toCardDocument(Card card) {
        if (card == null) {
            return null;
        }

        return new CardDocument(
                card.getSuit().name(),
                card.getRank().name()
        );
    }

    private static Card toCard(CardDocument document) {
        if (document == null) {
            return null;
        }

        return new Card(
                CardSuit.valueOf(document.getSuit()),
                CardRank.valueOf(document.getRank())
        );
    }
}
