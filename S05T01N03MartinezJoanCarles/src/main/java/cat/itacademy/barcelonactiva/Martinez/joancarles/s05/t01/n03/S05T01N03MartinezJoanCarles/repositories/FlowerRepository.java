package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.repositories;

import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerDTO;
import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerRequestDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FlowerRepository {

    private final WebClient webClient;

    @Autowired
    public FlowerRepository(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:9001").build();
    }

    public Mono<FlowerDTO> addFlower(String nameFlower, String countryFlower) {
        FlowerRequestDTO requestDTO = new FlowerRequestDTO(nameFlower, countryFlower);
        return webClient.post()
                .uri("/add")
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(FlowerDTO.class);
    }

    public Mono<FlowerDTO> updateFlower(Long id, FlowerDTO flowerDTO) {
        return webClient.put()
                .uri("/update/{id}", id)
                .bodyValue(flowerDTO)
                .retrieve()
                .bodyToMono(FlowerDTO.class);
    }

    public Mono<Void> deleteFlower(Long id) {
        return webClient.delete()
                .uri("/delete/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<FlowerDTO> getOneFlower(Long id) {
        return webClient.get()
                .uri("/getOne/{id}", id)
                .retrieve()
                .bodyToMono(FlowerDTO.class);
    }

    public Flux<FlowerDTO> getAllFlowers() {
        return webClient.get()
                .uri("/getAll")
                .retrieve()
                .bodyToFlux(FlowerDTO.class);
    }


}
