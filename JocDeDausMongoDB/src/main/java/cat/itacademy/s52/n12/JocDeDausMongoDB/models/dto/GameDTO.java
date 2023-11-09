package cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto;


import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO implements Serializable {
    @Field("_id")
    private String id;

    private short dice1;
    private short dice2;
    private Game.ResultGame result;
    private String playerName;
}

