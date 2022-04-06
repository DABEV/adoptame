package mx.edu.utez.adoptame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.edu.utez.adoptame.service.MascotaServiceImp;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    MascotaServiceImp mascotaServiceImp;


    @GetMapping("/consultarTodas")
    public String consultarMascotas(Model model){
        model.addAttribute("listaMascotas", mascotaServiceImp.listarMascotas());
        return "mascota/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(){
        return "";
    }

    @PostMapping("/guardarMascota")
    public String guardarMascota(){
        return "";
    }

    @PostMapping("/borrarMascota")
    public String borrarMascota(){
        return "";
    }
    
}
