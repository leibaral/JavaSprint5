package cat.itacademy.s52.n11.JocDeDausMySQL.models.repositories;

import cat.itacademy.s52.n11.JocDeDausMySQL.models.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByPlayerName(String playerName);
    boolean existsByPlayerID(Long playerID);

}

//Podrem fer ús de la validació per nom i/o per ID a cada implementació.