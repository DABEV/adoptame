package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {
    
    @GetMapping("/adoptador")
    public String adoptador () {
        return "/solicitud/adoptador";
    }

    @GetMapping("/voluntario")
    public String voluntario () {
        return "/solicitud/voluntario";
    }
    
}
