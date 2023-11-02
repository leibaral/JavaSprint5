package cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    private String playerID;
    private String playerName;
    private LocalDateTime registration;
    private String winRate;

    public PlayerDTO(String playerName){
        this.playerName = playerName;
    }
}
