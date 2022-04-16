package mx.edu.utez.adoptame.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping("/consultarTodos")
    public String consultarTodos() {
        return "";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica() {
        return "";
    }

    @GetMapping("/miCuenta")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String miCuenta(Authentication authentication, Model model, @ModelAttribute("usuario") UsuarioDto usuarioDto) {
        try {
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());
            usuarioDto = modelMapper.map(usuario, UsuarioDto.class);

            if (usuarioDto != null){
                model.addAttribute("usuario", usuarioDto);
                return "usuario/miCuenta";
            }
            return REDIRECT_LOGOUT;
        } catch (Exception e) {
            logger.error("Error al intentar cargar la cuenta del usuario");
        }

        return REDIRECT_LOGOUT;
    }

}
