package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.Services;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.DTO.SucursalDTO;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.Repositories.ISucursalRepository;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.models.Sucursal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {
    @Autowired
    private ISucursalRepository sucursalRepository;

    //Pas de Model(Entity guardada) a DTO :
    private SucursalDTO convertToDTO(Sucursal savedSucursal) {
        SucursalDTO sucursalDTO = new SucursalDTO();
        sucursalDTO.setPk_SucursalID(savedSucursal.getPk_SucursalID());
        sucursalDTO.setNomSucursal(savedSucursal.getNomSucursal());
        sucursalDTO.setPaisSucursal(savedSucursal.getPaisSucursal());
        sucursalDTO.setTipusSucursal(savedSucursal.getTipusSucursal());
        return sucursalDTO;
    }

    //Pas de DTO a Model(Entity) :
    private Sucursal convertToEntity(SucursalDTO sucursalDTO) {
        Sucursal sucursal = new Sucursal();
        sucursal.setPk_SucursalID(sucursalDTO.getPk_SucursalID());
        sucursal.setNomSucursal(sucursalDTO.getNomSucursal());
        sucursal.setPaisSucursal(sucursalDTO.getPaisSucursal());
        sucursal.setTipusSucursal(sucursalDTO.getTipusSucursal());
        return sucursal;
    }

    //Guardem una sucursal nova (a Model/Entity) i retornem la seva DTO :
    public SucursalDTO saveSucursal(SucursalDTO sucursalDTO){
        sucursalDTO.determinarTipusSucursal();
        Sucursal sucursal= convertToEntity(sucursalDTO);
        Sucursal savedSucursal = sucursalRepository.save(sucursal);
        return convertToDTO(savedSucursal);
    }

    public List<SucursalDTO> getAllSucursals(){
        List<Sucursal> allSucursals = sucursalRepository.findAll();
        return allSucursals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SucursalDTO getSucursalById(Long id){
        Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No hi ha cap sucursal amb aquest ID"));
        return convertToDTO(sucursal);
    }

    public SucursalDTO updateSucursal(Long id, SucursalDTO sucursalDTO){
        Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No hi ha cap sucursal amb aquest ID"));
        sucursal.setNomSucursal(sucursalDTO.getNomSucursal());
        sucursal.setPaisSucursal(sucursalDTO.getPaisSucursal());
        sucursalDTO.determinarTipusSucursal();
        sucursal.setTipusSucursal(sucursalDTO.getTipusSucursal());
        Sucursal savedSucursal = sucursalRepository.save(sucursal);
        return convertToDTO(savedSucursal);
    }

    public void deleteSucursal(Long id) {
        sucursalRepository.deleteById(id);
    }
}
