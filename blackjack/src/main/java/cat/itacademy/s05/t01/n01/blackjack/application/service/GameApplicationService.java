package cat.itacademy.s05.t01.n01.blackjack.application.service;

import cat.itacademy.s05.t01.n01.blackjack.application.dto.query.RankingItem;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DeckShuffler;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DomainEventPublisher;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GameApplicationService {
    private final GameRepository gameRepository;
    private final RankingProjectionRepository rankingProjectionRepository;
    private final DeckShuffler deckShuffler;

    public GameApplicationService(GameRepository gameRepository,
                                  RankingProjectionRepository rankingProjectionRepository,
                                  DeckShuffler deckShuffler) {
        this.gameRepository = gameRepository;
        this.rankingProjectionRepository = rankingProjectionRepository;
        this.deckShuffler = deckShuffler;
    }

    public Mono<Game> createGame(String playerName) {
        Deck orderedDeck = Deck.standard52CardDeck();
        List<Card> shuffledCards = deckShuffler.shuffle(orderedDeck.getRemainingCards());
        Deck shuffledDeck = new Deck(shuffledCards);

        Game game = Game.startNew(playerName, shuffledDeck);
        return gameRepository.save(game);
    }

    public Mono<Game> getGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)));
    }

    public Mono<Game> hit(String gameId) {
        return getGame(gameId)
                .flatMap(game -> {
                    game.hit();
                    return gameRepository.save(game);
                });
    }

    public Mono<Game> stand(String gameId) {
        return getGame(gameId)
                .flatMap(game -> {
                    game.stand();
                    return gameRepository.save(game);
                });
    }

    public Flux<RankingItem> getRanking() {
        return rankingProjectionRepository.getRanking();
    }
}