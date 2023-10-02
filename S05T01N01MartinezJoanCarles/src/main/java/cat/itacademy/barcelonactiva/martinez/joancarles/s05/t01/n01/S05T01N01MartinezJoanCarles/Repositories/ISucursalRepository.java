package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.Repositories;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal, Long> {
}
