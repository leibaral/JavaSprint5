package cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Data
@Document(collection="counters")
@NoArgsConstructor
@AllArgsConstructor
public class Counter {
    @Id
    private String id;  // Aquest serà "player" o "game" segons l'entitat
    private long seq;    // Valor actual de l'incrementador
}
