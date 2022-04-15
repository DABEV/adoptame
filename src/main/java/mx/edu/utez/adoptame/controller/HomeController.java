package mx.edu.utez.adoptame.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.dto.RolDto;
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
    public String inicio (Model model) {
        model.addAttribute("recientes", mascotaServiceImp.obtenerRecientes());
        model.addAttribute("noticias", blogServiceImp.listaPrincipales());
        return "inicio";
    }

    @GetMapping("/index")
	public String mostrarIndex(Authentication authentication, HttpSession session) {
		UsuarioDto usuario = modelMapper.map(usuarioServiceImp.buscarPorCorreo(authentication.getName()), UsuarioDto.class);
		
        // Añade los datos del usuario a la sesión 
		session.setAttribute("usuario", usuario);
		return "redirect:/";
	}

    @GetMapping("/login")
    public String login () {
        return "login";
    }

    @GetMapping("/signup")
    public String signup (Model model, @ModelAttribute("usuario") UsuarioDto usuarioDto) {
        return "signup";
    }

    @PostMapping("/signup")
	public String guardarUsuario(@RequestParam("tipoUsuario") String tipoUsuario, @ModelAttribute("usuario") UsuarioDto usuarioDto, RedirectAttributes redirectAttributes) {

        try {
            // Encriptar la contraseña
            String contrasenaEncriptada = passwordEncoder.encode(usuarioDto.getContrasena());
    
            // Asignar la contraseña encriptada
            usuarioDto.setContrasena(contrasenaEncriptada);
    
            // Aplicar tratemiento al telefono para solo guardar los numeros
            String telefono = usuarioDto.getTelefono().replaceAll("[\\s\\(\\)\\-]+", "");
            usuarioDto.setTelefono(telefono);
    
            // Habilitar la cuenta por defecto
            usuarioDto.setHabilitado(true);
 
            Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
            
            // Asignar un rol de usuarioDto de acuerdo al tipo seleccionado en el formulario
            usuario.addRol(rolServiceImp.buscarPorNombre(tipoUsuario.equals("voluntario") ? "ROL_VOLUNTARIO": "ROL_ADOPTADOR"));
    
            boolean respuesta = usuarioServiceImp.guardarUsuario(usuario) != null;

            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso! Por favor inicia sesión.");
                return REDIRECT_LOGIN;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
                return "redirect:/signup";
            }
        } catch (Exception e) {
            logger.error("Error al intentar crear una nueva cuenta");
        }

        return REDIRECT_LOGIN;
	}
    
    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO') or hasAuthority('ROL_ADOPTADOR')")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);
            redirectAttributes.addFlashAttribute("msg_success", "¡Sesión cerrada! Hasta luego");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cerrar la sesión, intenta de nuevo.");
            logger.error("Error al intentar cerrar sesión");
        }
        
        return REDIRECT_LOGIN;
    }
}
