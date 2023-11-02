package cat.itacademy.s52.n11.JocDeDausMySQL.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long playerID;
    private String playerName;
    private LocalDateTime registration;
    private String winRate;

    public PlayerDTO(String playerName){
        this.playerName = playerName;
    }
}
