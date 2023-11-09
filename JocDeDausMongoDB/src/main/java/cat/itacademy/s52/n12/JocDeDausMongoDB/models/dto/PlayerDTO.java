package cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    @Id
    @Field("_id")
    private String id;

    private String playerName;
    private LocalDateTime registration;
    private String winRate;

}
