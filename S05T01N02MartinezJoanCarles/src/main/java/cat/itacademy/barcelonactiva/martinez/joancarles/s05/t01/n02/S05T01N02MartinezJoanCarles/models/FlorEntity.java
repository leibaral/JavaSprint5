package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="flors")
@Data
@NoArgsConstructor
public class FlorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_FlorID;
    private String nomFlor;
    private String paisFlor;

}
