package cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {
    boolean existsByPlayerName(String playerName);
    boolean existsById(String id);
    Optional<Player> findById(String id);
    Player findFirstByOrderByIdDesc();

}