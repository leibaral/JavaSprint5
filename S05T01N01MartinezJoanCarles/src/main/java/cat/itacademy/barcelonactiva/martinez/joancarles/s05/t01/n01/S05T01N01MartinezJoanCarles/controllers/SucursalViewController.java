package cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.controllers;

import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.DTO.SucursalDTO;
import cat.itacademy.barcelonactiva.martinez.joancarles.s05.t01.n01.S05T01N01MartinezJoanCarles.Services.SucursalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sucursal")
public class SucursalViewController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping("/list")
    public String mostrarVistaList(Model model){
        List<SucursalDTO> llistaSucursals = sucursalService.getAllSucursals();
        /* la classe Model em permet lligar el recurs a mostrar (llistaSucursals)
            amb COM (atribut html) el Thymeleaf el cridarà: "sucursals" : */
        model.addAttribute("sucursals", llistaSucursals);
        return "sucursal/list";
    }

    /* add + save van de la maneta:
            primer preguntem amb el formulari (add) i
            després, en prémer el botó Guardar, guardem a la bb.dd. via save (POST) */
    @GetMapping("/add")
    public String mostrarVistaAdd(Model model){
        model.addAttribute("sucursal", new SucursalDTO());
        return "sucursal/add";
    }
    @PostMapping({"/save", "/add"})
    public String saveSucursal(@ModelAttribute SucursalDTO sucursalDTO, HttpServletRequest request) {
        sucursalDTO.determinarTipusSucursal();
        sucursalService.saveSucursal(sucursalDTO);
        return "redirect:" + request.getContextPath() + "/sucursal/list";
    }

    /* edit + update van de la maneta:
        primer preguntem amb el formulari (edit) i
        després, en prémer el botó Guardar, guardem a la bb.dd. via update (POST) */
    @GetMapping("edit/{id}")
    public String mostrarVistaEdit(@PathVariable Long id, Model model){
        SucursalDTO sucursalDTOaEditar = sucursalService.getSucursalById(id);
        model.addAttribute("sucursal", sucursalDTOaEditar);
        return "sucursal/edit";
    }
    @PostMapping("/update")
    public String updateSucursalEditada(SucursalDTO sucursalDTO, HttpServletRequest request){
        SucursalDTO sucursalEditada = sucursalService.updateSucursal(sucursalDTO.getPk_SucursalID(), sucursalDTO);
        return "redirect:" + request.getContextPath() + "/sucursal/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSucursal(@PathVariable Long id, HttpServletRequest request){
        sucursalService.deleteSucursal(id);
        return "redirect:" + request.getContextPath() + "/sucursal/list";
    }

}
