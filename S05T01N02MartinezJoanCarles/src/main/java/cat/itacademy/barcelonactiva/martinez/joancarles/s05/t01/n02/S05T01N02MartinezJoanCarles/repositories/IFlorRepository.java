package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.repositories;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.models.FlorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlorRepository extends JpaRepository<FlorEntity, Long> {
}
