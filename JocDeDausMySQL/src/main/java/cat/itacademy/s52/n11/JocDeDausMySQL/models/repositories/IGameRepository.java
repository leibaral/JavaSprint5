package cat.itacademy.s52.n11.JocDeDausMySQL.models.repositories;

import cat.itacademy.s52.n11.JocDeDausMySQL.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGameRepository extends JpaRepository<Game, Long> {
    default boolean isEmpty(){
        return count() == 0;
    }
}
// Podrem utilitzar aquest mètode en les implementacions per saber si la taula games és buida.
