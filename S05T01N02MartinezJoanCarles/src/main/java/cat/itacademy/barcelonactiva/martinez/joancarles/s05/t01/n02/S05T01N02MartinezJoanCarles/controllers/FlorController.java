package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.controllers;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.DTO.FlorDTO;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.services.FlorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class FlorController {

    @Autowired
    private FlorService florService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<FlorDTO> getOneFlor(@PathVariable Long id) {
        FlorDTO florDTO = florService.getFlorById(id);
        return new ResponseEntity<>(florDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FlorDTO>> getFlors(){
        List<FlorDTO> flors = florService.getAllFlors();
        return new ResponseEntity<>(flors, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FlorDTO> addFlor(@RequestBody FlorDTO florDTO){
        FlorDTO novaFlor = florService.saveFlor(florDTO);
        return new ResponseEntity<>(novaFlor, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FlorDTO> updateFlor(@PathVariable Long id, @RequestBody FlorDTO florDTO){
        FlorDTO updatedFlor = florService.updateFlor(id, florDTO);
        return new ResponseEntity<>(updatedFlor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FlorDTO> deleteFlor(@PathVariable Long id){
        florService.deleteFlor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
