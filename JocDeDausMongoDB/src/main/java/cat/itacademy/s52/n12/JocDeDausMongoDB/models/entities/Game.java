package cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;


@Entity
@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    @Id
    private String _id;


    private short dice1;    //variable per al resultat de "tirar" el primer dau
    private short dice2;    //variable per al resultat de "tirar" el segon dau
    private ResultGame result;
    private Player player;

    public Game(Player player){
        this.player= player;
        this._id = String.valueOf(player.getGamesList().size() + 1);
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

    /**
     * Generem una tirada de qualsevol dau amb un valor aleatori.
     */
    public static short rollDice() {
        Random random = new Random();
        return (short) (random.nextInt(6) + 1); //el "bound" o límit és exclusiu, el zero no.
    }

    /**
     * Obtenim el resultat de la partida,
     * condicionem la regla per guanyar (sumar 7)
     * i assignem l'enum de Game: WON_GAME o LOST_GAME.
     */
    public static Game.ResultGame obtainResult(short dice1, short dice2) {
        if (dice1 + dice2 == 7) {
            return Game.ResultGame.WON_GAME;
        } else {
            return Game.ResultGame.LOST_GAME;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "game_id=" + _id +
                ", dice1=" + dice1 +
                ", dice2=" + dice2 +
                ", result=" + result +
                ", player=" + player.getId() +
                '}';
    }

}
