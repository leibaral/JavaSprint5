package cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {
    boolean existsByPlayerName(String playerName);
    Player findByPlayerID(String playerID);
    boolean existsByPlayerID(String playerID);

}

//Podrem fer ús de la validació per nom i/o per ID a cada implementació.
