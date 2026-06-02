package cat.itacademy.s05.t01.n01.blackjack.infrastructure.config;

import cat.itacademy.s05.t01.n01.blackjack.application.service.GameApplicationService;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DomainEventPublisher;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.DeckShuffler;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack.domain.port.RankingProjectionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GameApplicationService gameApplicationService(GameRepository gameRepository,
                                                         RankingProjectionRepository rankingProjectionRepository,
                                                         DeckShuffler deckShuffler) {
        return new GameApplicationService(
                gameRepository,
                rankingProjectionRepository,
                deckShuffler
        );
    }
}
