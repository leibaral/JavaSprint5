package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class SucursalDTO {
    private Long pk_SucursalID;
    private String nomSucursal;
    private String paisSucursal;
    private String tipusSucursal;

    private static final List<String> paisosUE = Arrays.asList(
            "Alemania", "Austria", "Belgica", "Bulgaria", "Xipre", "Croacia", "Dinamarca",
            "Eslovaquia", "Eslovenia", "Espanya", "Estonia", "Finlandia", "França", "Grecia",
            "Hungría", "Irlanda", "Italia", "Letonia", "Lituania", "Luxemburg", "Malta",
            "Paisos Baixos", "Polonia", "Portugal", "Republica Txeca", "Rumanía", "Suecia"
    );

    public void determinarTipusSucursal() {
        this.tipusSucursal = paisosUE.contains(this.paisSucursal) ? "UE" : "Fora UE";
    }
}
