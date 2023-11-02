package cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import static cat.itacademy.s52.n12.JocDeDausMongoDB.models.services.GameServiceImpl.obtainResult;
import static cat.itacademy.s52.n12.JocDeDausMongoDB.models.services.GameServiceImpl.rollDice;

@Entity
@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    @Id
    private String gameID;
    private short dice1;    //variable per al resultat de "tirar" el primer dau
    private short dice2;    //variable per al resultat de "tirar" el segon dau
    private ResultGame result;
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
