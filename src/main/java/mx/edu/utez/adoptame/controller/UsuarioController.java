package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/consultarTodos")
    public String consultarTodos(){
        return "";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(){
        return "";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(){
        return "";
    }

    @PostMapping("/borrarUsuario")
    public String borrarUsuario(){
        return "";
    }
    
}
