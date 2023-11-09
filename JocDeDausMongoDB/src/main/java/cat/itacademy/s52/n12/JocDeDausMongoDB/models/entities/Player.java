package cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection="players")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @Field("_id")
    private String id;
    private String playerName;
    private List<Game> gamesList = new ArrayList<>();
    private LocalDateTime registration;

}

