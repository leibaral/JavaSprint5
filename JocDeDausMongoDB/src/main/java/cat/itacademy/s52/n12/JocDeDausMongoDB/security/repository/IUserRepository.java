package cat.itacademy.s52.n12.JocDeDausMongoDB.security.repository;
import cat.itacademy.s52.n12.JocDeDausMongoDB.security.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

}
