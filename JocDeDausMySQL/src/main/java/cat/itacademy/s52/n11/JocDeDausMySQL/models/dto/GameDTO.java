package cat.itacademy.s52.n11.JocDeDausMySQL.models.dto;


import cat.itacademy.s52.n11.JocDeDausMySQL.models.entities.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Long gameID;
    private short dice1;
    private short dice2;
    private Game.ResultGame result;
    private String playerName;
}
