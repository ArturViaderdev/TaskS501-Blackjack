package cat.itacademy.s05.t01.n01.blackjack.infrastructure.input.rest.dto;

import java.util.List;

public record GameResponse
        (
                String id,
                String playerName,
                List<CardResponse> playerCards,
                int playerScore,
                List<CardResponse> dealerCards,
                Integer dealerVisibleScore,
                String status,
                String result
        ) {
}
