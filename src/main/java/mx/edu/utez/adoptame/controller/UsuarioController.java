package mx.edu.utez.adoptame.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    private static final String VIEW_MI_CUENTA = "usuario/miCuenta";
    private static final String MSG_ERROR = "msg_error";
    private static final String REDIRECT_LOGOUT = "redirect:/logout";
    private static final String REDIRECT_MI_CUENTA = "redirect:/" + VIEW_MI_CUENTA;

    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping("/actualizar")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String actualizar () {
        return REDIRECT_MI_CUENTA;    
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String actualizar (Authentication authentication, RedirectAttributes redirectAttributes, @RequestParam("contrasena") String contrasena, @RequestParam("nuevaContrasena") String nuevaContrasena, @RequestParam("repetirContrasena") String repetirContrasena, @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, BindingResult result) {
        try {
            if (result.hasErrors())  {
                redirectAttributes.addFlashAttribute(MSG_ERROR, "Error en algunos campos, favor de verificarlos.");
                return VIEW_MI_CUENTA;
            }

            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());

            // Caso donde quieren cambiar la contraseña
            if (contrasena != null  && (!contrasena.isEmpty() || !contrasena.isBlank())) {
                if (nuevaContrasena != null && nuevaContrasena.equals(repetirContrasena) && passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                    System.out.println("Actualice la contraseña");
                    // Si la nueva contraseña coincide con la repetición
                    usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));    
                } else {
                    System.out.println("Las contraseñas no coinciden, favor de intentarlo de nuevo.");
                    redirectAttributes.addFlashAttribute(MSG_ERROR, "Las contraseñas no coinciden, favor de intentarlo de nuevo.");
                }
            }

            // Actualiza los demás datos
            if (usuario != null && usuarioDto != null) {
                usuario.setNombre(usuarioDto.getNombre());
                usuario.setApellidos(usuarioDto.getApellidos());
                usuario.setDireccion(usuarioDto.getDireccion());
                usuario.setTelefono(usuarioDto.getTelefono().replaceAll("[\\s\\(\\)\\-]+", ""));

                if (usuarioServiceImp.guardarUsuario(usuario) != null) {
                    System.out.println("msg_success Información del usuario actualizada correctamente.");
                    redirectAttributes.addFlashAttribute("msg_success", "Información del usuario actualizada correctamente.");                
                } else {
                    System.out.println("Error al actualizar el usuario, favor de intentarlo más tarde.");
                    redirectAttributes.addFlashAttribute(MSG_ERROR, "Error al actualizar el usuario, favor de intentarlo más tarde.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return REDIRECT_MI_CUENTA;
        }

        return VIEW_MI_CUENTA;
    }

    @GetMapping("/miCuenta")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String miCuenta(Authentication authentication, Model model) {
        try {
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());

            UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);
            
            model.addAttribute("usuario", usuarioDto);

            if (usuarioDto != null){
                model.addAttribute("usuario", usuarioDto);
                return VIEW_MI_CUENTA;
            }

            return REDIRECT_LOGOUT;
        } catch (Exception e) {
            logger.error("Error al intentar cargar la cuenta del usuario");
        }

        return REDIRECT_LOGOUT;
    }

}
