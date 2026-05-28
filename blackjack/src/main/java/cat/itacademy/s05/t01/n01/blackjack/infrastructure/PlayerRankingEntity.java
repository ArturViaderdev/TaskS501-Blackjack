package cat.itacademy.s05.t01.n01.blackjack.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("player_ranking")
public class PlayerRankingEntity {

    @Id
    private Long id;

    @Column("player_name")
    private String playerName;

    @Column("games_played")
    private Integer gamesPlayed;

    @Column("games_won")
    private Integer gamesWon;

    @Column("score")
    private Integer score;

    public PlayerRankingEntity() {
    }

    public PlayerRankingEntity(Long id, String playerName, Integer gamesPlayed, Integer gamesWon, Integer score) {
        this.id = id;
        this.playerName = playerName;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public Integer getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
