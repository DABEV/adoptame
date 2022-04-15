package mx.edu.utez.adoptame.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    private static final String REDIRECT_LOGOUT = "redirect:/logout";

    @GetMapping("/consultarTodos")
    public String consultarTodos() {
        return "";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica() {
        return "";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario() {
        return "";
    }

    @PostMapping("/borrarUsuario")
    public String borrarUsuario() {
        return "";
    }

    @GetMapping("/miCuenta")
    public String miCuenta(Authentication authentication, @ModelAttribute("usuario") UsuarioDto usuarioDto,
            Model model) {
        try {
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());
            usuarioDto = modelMapper.map(usuario, UsuarioDto.class);
            if (usuarioDto != null) {
                model.addAttribute("usuario", usuarioDto);
                return "usuario/miCuenta";
            }
            return REDIRECT_LOGOUT;
        } catch (Exception e) {
            // log
        }

        return REDIRECT_LOGOUT;
    }

}
