package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mascota")
public class MascotaController {


    @GetMapping("/consultarTodas")
    public String consultarMascotas(){
        return "";
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
