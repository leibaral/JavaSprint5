package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="sucursal")
@Getter
@Setter
@NoArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_SucursalID;
    private String nomSucursal;
    private String paisSucursal;
    private String tipusSucursal;

}
