package cat.itacademy.s52.n11.JocDeDausMySQL.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cat.itacademy.s52.n11.JocDeDausMySQL.models.services.GameServiceImpl.obtainResult;
import static cat.itacademy.s52.n11.JocDeDausMySQL.models.services.GameServiceImpl.rollDice;

@Entity
@Table(name = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameID;

    @Column(name = "dice1", nullable = false)
    private short dice1;    //variable per al resultat de "tirar" el primer dau

    @Column(name = "dice2", nullable = false)
    private short dice2;    //variable per al resultat de "tirar" el segon dau

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false, columnDefinition = "ENUM('WON_GAME', 'LOST_GAME')")
    private ResultGame result;

    /**
     * @ManyToOne estableix relació: many games = 1player
     *
     * El Player no es carregarà automàticament quan es carregui cada entitat Game, gràcies al LAZY fetching...
     * ...Per carregar-lo caldrà el seu getter.
     * @JoinColumn especifica el nom de la columna de la taula "games" que s'utilitzarà per guardar
     * l'ID (la foreign key) de l'entitat Player relacionada (simulant el JOIN).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player")
    private Player player;

    public Game(Player player){
        this.player= player;
        this.dice1 = rollDice();
        this.dice2 = rollDice();
        this.result = obtainResult(dice1, dice2);
    }

    /**
     * Definim els valors "enum" que formen part de la lògica del joc i persistir-los (a diferència
     * de guardar-los en una classe DTO) per poder-los recuperar quan calgui o treballar amb ells.
     */
    public enum ResultGame {
        WON_GAME,
        LOST_GAME
    }

    @Override
    public String toString() {
        return "Game{" +
                "game_id=" + gameID +
                ", dice1=" + dice1 +
                ", dice2=" + dice2 +
                ", result=" + result +
                ", player=" + player.getPlayerID() +
                '}';
    }

}
