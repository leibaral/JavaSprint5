package cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGameRepository extends MongoRepository<Game, String> {
    default boolean isEmpty(){
        return count() == 0;
    }
    List<Game> findByPlayerId(String playerId);
}
// Podrem utilitzar aquest mètode en les implementacions per saber si hi ha games a la bbdd.

