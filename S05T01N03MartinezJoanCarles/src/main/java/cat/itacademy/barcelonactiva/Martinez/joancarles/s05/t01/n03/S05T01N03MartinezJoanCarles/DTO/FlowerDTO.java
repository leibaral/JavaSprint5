package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FlowerDTO {
    @JsonProperty("pk_FlorID")
    private Long pk_FlowerID;
    @JsonProperty("nomFlor")
    private String nameFlower;
    @JsonProperty("paisFlor")
    private String countryFlower;
    @JsonProperty("tipusFlor")
    private String typeFlower;

    private static final List<String> countriesEU = Arrays.asList(
            "Alemania", "Austria", "Belgica", "Bulgaria", "Croacia", "Dinamarca",
            "Eslovaquia", "Eslovenia", "Espanya", "Estonia", "Finlandia", "França", "Grecia",
            "Hungría", "Irlanda", "Italia", "Letonia", "Lituania", "Luxemburg", "Malta",
            "Paisos Baixos", "Polonia", "Portugal", "Republica Txeca", "Rumanía", "Suecia", "Xipre"
    );

    public void determinarTypeFlower() {
        this.typeFlower = countriesEU.contains(this.countryFlower) ? "UE" : "Fora UE";
    }

}
