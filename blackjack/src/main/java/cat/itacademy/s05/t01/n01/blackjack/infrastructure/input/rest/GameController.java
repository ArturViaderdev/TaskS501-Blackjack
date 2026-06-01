package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto.CreateGameRequest;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto.GameResponse;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto.RankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.mapper.RestMapper;
import cat.itacademy.s05.t01.n01.blackjack.application.service.GameApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class GameController {
    private final GameApplicationService gameApplicationService;

    public GameController(GameApplicationService gameApplicationService) {
        this.gameApplicationService = gameApplicationService;
    }

    @PostMapping("/games")
    public Mono<ResponseEntity<GameResponse>> createGame(@RequestBody CreateGameRequest request) {
        return gameApplicationService.createGame(request.getPlayerName())
                .map(RestMapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping("/games/{id}")
    public Mono<ResponseEntity<GameResponse>> getGame(@PathVariable String id) {
        return gameApplicationService.getGame(id)
                .map(RestMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/games/{id}/hit")
    public Mono<ResponseEntity<GameResponse>> hit(@PathVariable String id) {
        return gameApplicationService.hit(id)
                .map(RestMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/games/{id}/stand")
    public Mono<ResponseEntity<GameResponse>> stand(@PathVariable String id) {
        return gameApplicationService.stand(id)
                .map(RestMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/ranking")
    public Flux<RankingResponse> getRanking() {
        return gameApplicationService.getRanking()
                .map(RestMapper::toResponse);
    }
}
