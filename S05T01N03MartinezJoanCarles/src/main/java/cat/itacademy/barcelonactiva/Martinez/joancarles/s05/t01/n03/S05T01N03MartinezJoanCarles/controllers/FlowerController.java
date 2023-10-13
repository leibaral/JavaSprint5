package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.controllers;

import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerDTO;
import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.DTO.FlowerRequestDTO;
import cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flor")
public class FlowerController {
    @Autowired
    private FlowerService flowerService;

    @GetMapping("/clientFlorsAdd")
    public Mono<ResponseEntity<FlowerDTO>> addFlower(@RequestBody FlowerRequestDTO flowerRequestDTO){
        return flowerService.saveFlower(flowerRequestDTO)
                .map(savedFlower -> new ResponseEntity<>(savedFlower, HttpStatus.CREATED));
    }

    @PutMapping("/clientFlorsUpdate/{id}")
    public Mono<ResponseEntity<FlowerDTO>> updateFlower(@PathVariable Long id, @RequestBody FlowerDTO flowerDTO){
        return flowerService.updateFlower(flowerDTO, id)
                .map(updatedFlower -> new ResponseEntity<>(updatedFlower, HttpStatus.OK));
    }

    @GetMapping("/clientFlorsGetOneById/{id}")
    public Mono<ResponseEntity<FlowerDTO>> getOneFlowerById(@PathVariable Long id){
        return flowerService.getOneFlowerById(id)
                .map(shownFlower -> new ResponseEntity<>(shownFlower, HttpStatus.OK));
    }

    @GetMapping("/clientFlorsAll")
    public ResponseEntity<Flux<FlowerDTO>> getAllFlowers(){
        Flux<FlowerDTO> flowers = flowerService.getAllFlowers();
        return ResponseEntity.ok(flowers);
    }

    @DeleteMapping("/clientFlorsDelete/{id}")
    public Mono<ResponseEntity<Void>> deleteFlower(@PathVariable Long id){
        return flowerService.deleteFlower(id)
                .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
    }
}
