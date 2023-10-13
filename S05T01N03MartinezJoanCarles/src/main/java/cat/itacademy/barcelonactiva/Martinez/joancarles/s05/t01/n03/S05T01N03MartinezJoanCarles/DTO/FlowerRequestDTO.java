package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowerRequestDTO {
    private String nameFlower;
    private String countryFlower;

    public FlowerRequestDTO(String nameFlower, String countryFlower){
        this.nameFlower = nameFlower;
        this.countryFlower = countryFlower;
    }
}

