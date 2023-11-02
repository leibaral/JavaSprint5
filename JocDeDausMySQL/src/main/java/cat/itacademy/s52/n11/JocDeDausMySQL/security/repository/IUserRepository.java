package cat.itacademy.s52.n11.JocDeDausMySQL.security.repository;
import cat.itacademy.s52.n11.JocDeDausMySQL.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
