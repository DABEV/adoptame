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
    public String actualizar() {
        return REDIRECT_MI_CUENTA;
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String actualizar(Authentication authentication, RedirectAttributes redirectAttributes,
            @RequestParam("contrasena") String contrasena, @RequestParam("nuevaContrasena") String nuevaContrasena,
            @RequestParam("repetirContrasena") String repetirContrasena,
            @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("msg_warning", "Error en algunos campos, favor de verificarlos.");
                return VIEW_MI_CUENTA;
            }

            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());

            // Caso donde quieren cambiar la contrase??a
            if (contrasena != null && (!contrasena.isEmpty() || !contrasena.isBlank())) {
                if (nuevaContrasena != null && nuevaContrasena.equals(repetirContrasena)
                        && passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                    // Si la nueva contrase??a coincide con la repetici??n
                    usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
                } else {
                    redirectAttributes.addFlashAttribute("msg_warning",
                            "Las contrase??as no coinciden, favor de intentarlo de nuevo.");
                }
            }

            // Actualiza los dem??s datos
            if (usuario != null && usuarioDto != null) {
                usuario.setNombre(usuarioDto.getNombre());
                usuario.setApellidos(usuarioDto.getApellidos());
                usuario.setDireccion(usuarioDto.getDireccion());
                usuario.setTelefono(usuarioDto.getTelefono().replaceAll("[\\s\\(\\)\\-]+", ""));

                if (usuarioServiceImp.guardarUsuario(usuario) != null) {
                    redirectAttributes.addFlashAttribute("msg_success",
                            "Informaci??n del usuario actualizada correctamente.");
                } else {
                    redirectAttributes.addFlashAttribute(MSG_ERROR,
                            "Error al actualizar el usuario, favor de intentarlo m??s tarde.");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return REDIRECT_MI_CUENTA;
        }

        return REDIRECT_MI_CUENTA;
    }

    @GetMapping("/miCuenta")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String miCuenta(Authentication authentication, Model model) {
        try {
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(authentication.getName());

            UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);

            model.addAttribute("usuario", usuarioDto);

            if (usuarioDto != null) {
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
