package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/mi-cuenta")
    public String miCuenta () {
        return "usuario/miCuenta";
    }
    
}
