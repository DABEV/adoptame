package mx.edu.utez.adoptame.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.model.Rol;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.BlogServiceImp;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.service.RolServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/")
public class HomeController {

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

    // Variables de redirección
    private static final String REDIRECT_LOGIN = "redirect:/login";

    @GetMapping("/")
    public String inicio (Model model) {
        model.addAttribute("recientes", mascotaServiceImp.obtenerRecientes());
        model.addAttribute("noticias", blogServiceImp.listaPrincipales());
        return "inicio";
    }

    @GetMapping("/index")
	public String mostrarIndex(Authentication authentication, HttpSession session) {
		String correo = authentication.getName();

		Usuario usuario = usuarioServiceImp.buscarPorCorreo(correo);
		
        // Añade los datos del usuario a la sesión 
		session.setAttribute("usuario", usuario);
		return "redirect:/";
	}

    @GetMapping("/login")
    public String login () {
        return "login";
    }

    @GetMapping("/signup")
    public String signup (Model model, Usuario usuario) {
        return "signup";
    }

    @PostMapping("/signup")
	public String guardarUsuario(@RequestParam("tipoUsuario") String tipoUsuario, Usuario usuario, RedirectAttributes redirectAttributes) {

        try {
            // Encriptar la contraseña
            String contrasenaEncriptada = passwordEncoder.encode(usuario.getContrasena());
    
            // Asignar la contraseña encriptada
            usuario.setContrasena(contrasenaEncriptada);
    
            // Aplicar tratemiento al telefono para solo guardar los numeros
            String telefono = usuario.getTelefono().replaceAll("[\\s\\(\\)\\-]+", "");
            usuario.setTelefono(telefono);
    
            // Habilitar la cuenta por defecto
            usuario.setHabilitado(true);
    
            // Asignar un rol de usuario de acuerdo al tipo seleccionado en el formulario
            Rol rol = null;
            rol = rolServiceImp.buscarPorNombre(tipoUsuario.equals("voluntario") ? "ROL_VOLUNTARIO": "ROL_ADOPTADOR");

            usuario.addRol(rol);
    
            boolean respuesta = usuarioServiceImp.guardarUsuario(usuario) != null;

            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso! Por favor inicia sesión.");
                return REDIRECT_LOGIN;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
                return "redirect:/signup";
            }
        } catch (Exception e) {
            // log
        }

        return REDIRECT_LOGIN;
	}
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);
            redirectAttributes.addFlashAttribute("msg_success", "¡Sesión cerrada! Hasta luego");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cerrar la sesión, intenta de nuevo.");
        }
        
        return REDIRECT_LOGIN;
    }
}
