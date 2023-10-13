package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.services;

import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerDTO;
import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerRequestDTO;
import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlowerService {
    @Autowired
    private final FlowerRepository flowerRepository;

    public FlowerService(FlowerRepository flowerRepository){
        this.flowerRepository = flowerRepository;
    }

    public Mono<FlowerDTO> saveFlower(FlowerRequestDTO flowerRequestDTO) {
        return flowerRepository.addFlower(flowerRequestDTO.getNameFlower(), flowerRequestDTO.getCountryFlower());
    }

    public Flux<FlowerDTO> getAllFlowers(){
        return flowerRepository.getAllFlowers();
    }

    public Mono<FlowerDTO> getOneFlowerById(Long id){
        return flowerRepository.getOneFlower(id);
    }

    public Mono<FlowerDTO> updateFlower(FlowerDTO flowerDTO, Long id){
        return flowerRepository.updateFlower(id, flowerDTO);
    }

    public Mono<Void> deleteFlower(Long id){
        return flowerRepository.deleteFlower(id);
    }

}
