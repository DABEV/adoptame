package mx.edu.utez.adoptame.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.dto.DonacionDto;
import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.BlogServiceImp;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.service.RolServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_SINGUP = "redirect:/signup";

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @Autowired
    private RolServiceImp rolServiceImp;

    @Autowired
    private MascotaServiceImp mascotaServiceImp;

    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("/")
    public String inicio(Model model, @ModelAttribute("donacion") DonacionDto donacionDto) {
        model.addAttribute("recientes", mascotaServiceImp.obtenerRecientes());
        model.addAttribute("noticias", blogServiceImp.listaPrincipales());
        return "inicio";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication authentication, HttpSession session) {
        UsuarioDto usuario = modelMapper.map(usuarioServiceImp.buscarPorCorreo(authentication.getName()),
                UsuarioDto.class);

        // A??ade los datos del usuario a la sesi??n
        session.setAttribute("usuario", usuario);
        usuarioServiceImp.procedimientoInicioSesion(usuario.getId());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model, @ModelAttribute("usuario") UsuarioDto usuarioDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, BindingResult result,
            @RequestParam("tipoUsuario") String tipoUsuario,
            RedirectAttributes redirectAttributes,
            HttpSession session, @RequestParam("confirmarContrasena") String repeticion) {

        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("msg_warning", "Error en algunos campos, favor de verificarlos.");
                return "signup";
            } else {
                if (usuarioDto.getContrasena().equals(repeticion)) {
                    // Encriptar la contrase??a
                    String contrasenaEncriptada = passwordEncoder.encode(usuarioDto.getContrasena());
                    // Asignar la contrase??a encriptada
                    usuarioDto.setContrasena(contrasenaEncriptada);
                    // Aplicar tratemiento al telefono para solo guardar los numeros
                    String telefono = usuarioDto.getTelefono().replaceAll("[\\s\\(\\)\\-]+", "");
                    usuarioDto.setTelefono(telefono);
                    // Habilitar la cuenta por defecto
                    usuarioDto.setHabilitado(true);
                    Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
                    // Asignar un rol de usuarioDto de acuerdo al tipo seleccionado en el formulario
                    usuario.addRol(rolServiceImp
                            .buscarPorNombre(tipoUsuario.equals("voluntario") ? "ROL_VOLUNTARIO" : "ROL_ADOPTADOR"));

                    boolean respuesta = usuarioServiceImp.guardarUsuario(usuario) != null;

                    if (respuesta) {
                        redirectAttributes.addFlashAttribute("msg_success",
                                "??Registro exitoso! Por favor inicia sesi??n.");
                        return REDIRECT_LOGIN;
                    } else {
                        redirectAttributes.addFlashAttribute("msg_error",
                                "??Registro fallido! Por favor intenta de nuevo.");
                        return REDIRECT_SINGUP;
                    }
                } else {
                    redirectAttributes.addFlashAttribute("msg_warning",
                            "Las contrase??as no coinciden, favor de verificar");
                    return REDIRECT_SINGUP;
                }
            }
        } catch (Exception e) {
            logger.error("Error al intentar crear una nueva cuenta");
        }

        return REDIRECT_LOGIN;
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute("usuario");
            Long idUsuario = usuarioDto.getId();
            usuarioServiceImp.procedimientoCerrarSesion(idUsuario, new Date());
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);

            redirectAttributes.addFlashAttribute("msg_success", "??Sesi??n cerrada! Hasta luego");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg_error",
                    "Ocurri?? un error al cerrar la sesi??n, intenta de nuevo.");
            logger.error("Error al intentar cerrar sesi??n");
        }

        return REDIRECT_LOGIN;
    }
}
