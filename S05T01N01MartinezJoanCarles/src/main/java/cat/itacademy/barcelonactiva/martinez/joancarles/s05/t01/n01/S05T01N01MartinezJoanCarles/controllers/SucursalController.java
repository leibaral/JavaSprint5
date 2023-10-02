package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.controllers;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.DTO.SucursalDTO;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.Services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<SucursalDTO> getOneSucursal(@PathVariable Long id) {
        SucursalDTO sucursalDTO = sucursalService.getSucursalById(id);
        return new ResponseEntity<>(sucursalDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SucursalDTO>> getSucursals(){
        List<SucursalDTO> sucursals = sucursalService.getAllSucursals();
        return new ResponseEntity<>(sucursals, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<SucursalDTO> addSucursal(@RequestBody SucursalDTO sucursalDTO){
        SucursalDTO novaSucursal = sucursalService.saveSucursal(sucursalDTO);
        return new ResponseEntity<>(novaSucursal, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SucursalDTO> updateSucursal(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO){
        SucursalDTO updatedSucursal = sucursalService.updateSucursal(id, sucursalDTO);
        return new ResponseEntity<>(updatedSucursal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SucursalDTO> deleteSucursal(@PathVariable Long id){
        sucursalService.deleteSucursal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
