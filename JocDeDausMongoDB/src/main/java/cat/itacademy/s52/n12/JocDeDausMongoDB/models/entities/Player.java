package cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities;


import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Document(collection="players")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    private String _id;

    private String playerName = "ANONIM";
    private List<Game> gamesList = new ArrayList<>();

    /**
     * @CreationTimestamp inicialitza automàticament hora-i-data en crear cada nova instància de Player.
     */
    @CreationTimestamp
    private LocalDateTime registration;

    public Player(String name){
        this.playerName = name;
        this.registration = LocalDateTime.now(); //aquí li diem que l'hora-i-data a registrar és ARA.
    }

    public void addGame(Game game){
        gamesList.add(game);
    }
}

