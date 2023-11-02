package cat.itacademy.s52.n11.JocDeDausMySQL.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="players")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerID;

    @Column(name = "playerName", nullable = false)
    private String playerName = "ANONIM";

    /**
     * @OneToMany estableix relació 1player = many games
     *
     * mappedBy estableix el "One" (player) amb el què es mapejarà cada instància dels Games a la llista
     * al mateix moment de carregar-se un "One" en memòria, tb ho fan els "Many", amb el fetch type Eager
     * en esborrar-se un "One", s'esborren automàticament els "Many" games, gràcies al orphanRemoval true
     * targetEntity estableix quina és la classe "Many" (Game en aquest cas)
     * cascadeType All indica que totes les operacions de cascada (creació, actualització i eliminació),
     * que s'apliquin a un player, també s'aplicaran automàticament a cada un dels seus Games.
     */
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Game.class, cascade = CascadeType.ALL)
    @Column(name = "gamesList")
    private List<Game> gamesList = new ArrayList<>();

    /**
     * @CreationTimestamp inicialitza automàticament hora-i-data en crear cada nova instància de Player.
     */
    @CreationTimestamp
    @Column(name = "player_registration_date", nullable = false)
    private LocalDateTime registration;

    public Player(String name){
        this.playerName = name;
        this.registration = LocalDateTime.now(); //aquí li diem que l'hora-i-data a registrar és ARA.
    }

    public void addGame(Game game){
        gamesList.add(game);
    }
}
