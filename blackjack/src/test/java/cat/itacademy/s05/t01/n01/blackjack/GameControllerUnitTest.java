package cat.itacademy.s05.t01.n01.blackjack;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto.GameResponse;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto.RankingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class GameControllerUnitTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateAndGetGame() {
        webTestClient.post()
                .uri("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "playerName": "Anna"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.playerName").isEqualTo("Anna");
    }

    String createGameAndGetId(){
        GameResponse created;
        do {
            created = webTestClient.post()
                    .uri("/games")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("""
                            {
                              "playerName": "Anna"
                            }
                            """)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(GameResponse.class)
                    .returnResult()
                    .getResponseBody();
        }while (created.status().equals("FINISHED"));
        return created.id();
    }

    @Test
    void shouldCreateGameAndThenReadIt() {
        String id = createGameAndGetId();
        webTestClient.get()
                .uri("/games/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(game -> {
                    assertEquals(id, game.id());
                    assertEquals("Anna", game.playerName());
                });
    }

    @Test
    void shouldHitGame() {
        String id = createGameAndGetId();
        webTestClient.post()
                .uri("/games/" + id + "/hit")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.status").exists();
    }

    @Test
    void shouldHitAndFinishGameGetScore(){
        String id = createGameAndGetId();
        GameResponse response;
        do{
                response = webTestClient.post()
                        .uri("/games/" + id + "/hit")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(GameResponse.class)
                        .returnResult()
                        .getResponseBody();

        }while(!(response.status().equals("FINISHED")));
        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RankingResponse.class)
                .value(list -> {
                    assertEquals(true, list.size() >= 1);
                    assertEquals("Anna",list.get(0).playerName());
                });
    }

    @Test
    void shouldStandGame() {
        String id = createGameAndGetId();
        webTestClient.post()
                .uri("/games/" + id + "/stand")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.status").isEqualTo("FINISHED")
                .jsonPath("$.result").exists();
    }

    @Test
    void shouldHitGameWithoutFinishingIt() {
        String id = createGameAndGetId();
        webTestClient.post()
                .uri("/games/" + id + "/hit")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id);
    }

    @Test
    void shouldReturnRanking() {
        String id = createGameAndGetId();
        webTestClient.post()
                .uri("/games/" + id + "/stand");
        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RankingResponse.class)
                .value(list -> {
                    assertEquals(true, list.size() >= 1);
                    assertEquals("Anna",list.get(0).playerName());
                });
    }

    @Test
    void shouldReturnNotFoundWhenGameDoesNotExist() {
        webTestClient.get()
                .uri("/games/999999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnGameWithAssertEquals() {
        String id = createGameAndGetId();
        webTestClient.get()
                .uri("/games/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(dto -> {
                    assertEquals(id, dto.id());
                    assertEquals("Anna", dto.playerName());
                });
    }
}
