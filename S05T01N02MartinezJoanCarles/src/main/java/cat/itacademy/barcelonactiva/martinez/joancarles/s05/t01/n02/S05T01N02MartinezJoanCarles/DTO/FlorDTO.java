package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.DTO;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FlorDTO {
    private Long pk_FlorID;
    private String nomFlor;
    private String paisFlor;
    private String tipusFlor;

    private static final List<String> paisosUE = Arrays.asList(
            "Alemania", "Austria", "Belgica", "Bulgaria", "Croacia", "Dinamarca",
            "Eslovaquia", "Eslovenia", "Espanya", "Estonia", "Finlandia", "França", "Grecia",
            "Hungría", "Irlanda", "Italia", "Letonia", "Lituania", "Luxemburg", "Malta",
            "Paisos Baixos", "Polonia", "Portugal", "Republica Txeca", "Rumanía", "Suecia", "Xipre"
    );

    public void determinarTipusFlor() {
        this.tipusFlor = paisosUE.contains(this.paisFlor) ? "UE" : "Fora UE";
    }
}
