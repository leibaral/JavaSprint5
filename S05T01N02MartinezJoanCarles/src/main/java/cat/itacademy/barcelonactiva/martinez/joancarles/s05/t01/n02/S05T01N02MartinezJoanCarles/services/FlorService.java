package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.services;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.DTO.FlorDTO;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.models.FlorEntity;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n02.S05T01N02MartinezJoanCarles.repositories.IFlorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlorService {

    @Autowired
    private IFlorRepository florRepository;

    //Pas de Model(Entity guardada) a DTO :
    private FlorDTO convertToDTO(FlorEntity savedFlor) {
        FlorDTO florConvertidaaDTO = new FlorDTO();
        florConvertidaaDTO.setPk_FlorID(savedFlor.getPk_FlorID());
        florConvertidaaDTO.setNomFlor(savedFlor.getNomFlor());
        florConvertidaaDTO.setPaisFlor(savedFlor.getPaisFlor());
        florConvertidaaDTO.determinarTipusFlor();
        return florConvertidaaDTO;
    }

    //Pas de DTO a Model(Entity) :
    private FlorEntity convertToEntity(FlorDTO savedFlorDTO) {
        FlorEntity florConvertidaaEntity = new FlorEntity();
        florConvertidaaEntity.setPk_FlorID(savedFlorDTO.getPk_FlorID());
        florConvertidaaEntity.setNomFlor(savedFlorDTO.getNomFlor());
        florConvertidaaEntity.setPaisFlor(savedFlorDTO.getPaisFlor());
        return florConvertidaaEntity;
    }

    //Guardem una flor nova (a Model/Entity) i retornem la seva DTO :
    public FlorDTO saveFlor(FlorDTO florDTO){
        florDTO.determinarTipusFlor();
        FlorEntity novaFlorEntity= convertToEntity(florDTO);
        FlorEntity savedFlor = florRepository.save(novaFlorEntity);
        return convertToDTO(savedFlor);
    }

    public List<FlorDTO> getAllFlors(){
        List<FlorEntity> allFlors = florRepository.findAll();
        return allFlors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FlorDTO getFlorById(Long id){
        FlorEntity flor = florRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No hi ha cap flor amb aquest ID"));
        return convertToDTO(flor);
    }

    public FlorDTO updateFlor(Long id, FlorDTO florDTO){
        FlorEntity flor = florRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No hi ha cap flor amb aquest ID"));
        flor.setNomFlor(florDTO.getNomFlor());
        flor.setPaisFlor(florDTO.getPaisFlor());
        florDTO.determinarTipusFlor();
        FlorEntity savedFlor = florRepository.save(flor);
        return convertToDTO(savedFlor);
    }

    public void deleteFlor(Long id) {
        florRepository.deleteById(id);
    }

}
