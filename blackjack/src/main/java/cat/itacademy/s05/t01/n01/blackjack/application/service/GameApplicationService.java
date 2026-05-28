package cat.itacademy.s05.t01.n01.blackjack.application.service;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.application.dto.RankingProjectionRepository;
import cat.itacademy.s05.t01.n01.blackjack.domain.event.DomainEventPublisher;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.exceptions.GameNotFoundException;

import java.util.List;

public class GameApplicationService {

    private final GameRepository gameRepository;
    private final RankingProjectionRepository rankingProjectionRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final DeckShuffler deckShuffler;

    public GameApplicationService(GameRepository gameRepository,
                                  RankingProjectionRepository rankingProjectionRepository,
                                  DomainEventPublisher domainEventPublisher,
                                  DeckShuffler deckShuffler) {
        this.gameRepository = gameRepository;
        this.rankingProjectionRepository = rankingProjectionRepository;
        this.domainEventPublisher = domainEventPublisher;
        this.deckShuffler = deckShuffler;
    }

    public Game createGame() {
        Deck orderedDeck = Deck.standard52CardDeck();
        List<Card> shuffledCards = deckShuffler.shuffle(orderedDeck.getRemainingCards());
        Deck shuffledDeck = new Deck(shuffledCards);

        Game game = Game.startNew(shuffledDeck);
        Game savedGame = gameRepository.save(game);
        domainEventPublisher.publish(savedGame.pullDomainEvents());

        return savedGame;
    }

    public Game getGame(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public Game hit(String gameId) {
        Game game = getGame(gameId);
        game.hit();

        Game savedGame = gameRepository.save(game);
        domainEventPublisher.publish(savedGame.pullDomainEvents());

        return savedGame;
    }

    public Game stand(String gameId) {
        Game game = getGame(gameId);
        game.stand();

        Game savedGame = gameRepository.save(game);
        domainEventPublisher.publish(savedGame.pullDomainEvents());

        return savedGame;
    }

    public List<RankingItem> getRanking() {
        return rankingProjectionRepository.getRanking();
    }
}
