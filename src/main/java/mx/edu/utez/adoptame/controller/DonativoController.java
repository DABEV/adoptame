package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donativo")
public class DonativoController {

    @GetMapping("/consultarTodos")
    public String consultarTodos(){
        return "";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(){
        return "";
    }

    @PostMapping("/guardarDonativo")
    public String guardarDonativo(){
        return "";
    }

    @PostMapping("/borrarDonativo")
    public String borrarDonativo(){
        return "";
    }
    
    
}
