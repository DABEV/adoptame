package mx.edu.utez.adoptame.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.service.EmailServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;
import mx.edu.utez.adoptame.dto.UsuarioDto;

@Controller
public class RecoverPasswordController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailServiceImp emailServiceImpl;

	@Autowired
	private UsuarioServiceImp userServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;

	String NUMEROS = "0123456789";
	String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	public String generarContrasena(int length) {
		return contrasenaAleatoria(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}

	public String contrasenaAleatoria(String key, int length) {
		String contrasena = "";
		for (int i = 0; i < length; i++) {
			contrasena += (key.charAt((int) (Math.random() * key.length())));
		}
		return contrasena;
	}

	@GetMapping("/reset/password")
	public String recuperarContrasena() {
		return "restablecerContraseña";
	}

	@PostMapping("/reset/password/email")
	public String enviarContrasenaEmail(@RequestParam("correo") String correo,
			RedirectAttributes redirectAttributes) {
		// Remove white spaces
		correo = correo.replaceAll("[\\s]", "");
		// Create new password with 12 characters
		String nuevaContrasena = generarContrasena(12);
		// Encoder password
		String contrasenaEncriptada = passwordEncoder.encode(nuevaContrasena);
		// Search user_name
		UsuarioDto user = modelMapper.map(userServiceImpl.buscarPorCorreo(correo), UsuarioDto.class);
		// Update password
		boolean respuestaCambio = userServiceImpl.cambiarContrasena(contrasenaEncriptada, user.getCorreo());
		// Get full user_name
		String nombreUsuario = user.getNombre().concat(" ").concat(user.getApellidos());
		// Create email content
		String htmlContent = plantillaRecuperacionContrasena(nombreUsuario, user.getCorreo(), nuevaContrasena);
		// Send message
		boolean respuestaEmail = emailServiceImpl.sendEmail(user.getCorreo(), "Cambio de contraseña", htmlContent);
		if (respuestaCambio && respuestaEmail) {
			redirectAttributes.addFlashAttribute("msg_success",
					"Correo de recuperación de contraseña enviado, por favor revisa tu bandeja de correo.");
			return "redirect:/login";
		} else {
			redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error, por favor intenta de nuevo.");
			return "redirect:/reset/password";
		}
	}

	public String plantillaRecuperacionContrasena(String nombreUsuario, String email, String contrasena) {
		StringBuilder contenidoCorreo = new StringBuilder();
		contenidoCorreo.append("<!DOCTYPE html>");
		contenidoCorreo.append("<html lang=\"en\">");
		contenidoCorreo.append("<head>");
		contenidoCorreo.append("<style>");
		contenidoCorreo.append(
				"p { color: #74422B; text-align: justify; font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; size: 30px;}.card { min-width: 0; background-color: #fff; background-clip: border-box; border: 1px solid rgba(0, 0, 0, .125); border-radius: 0.5rem;}.card-header { display: flex; flex-direction: column; padding: 0.75rem 1.5rem; margin-bottom: 0; background-color: hsla(0, 0%, 100%, 0); border-bottom: 1px solid rgba(0, 0, 0, .125);}.card-body { flex: 1 1 auto; padding: 1.5rem; }.card-title { margin-bottom: 0.3rem; font-size: 1.25rem; display: block; margin-block-start: 1.67em; margin-block-end: 1.67em; margin-inline-start: 0px; margin-inline-end: 0px;  }.mt-3 { margin-top: 1rem !important; }body { font-family: var(--mdb-font-roboto); }.list-group-flush { border-width: 0 0 1px; }.list-group-item { display: block; color: #262626; text-decoration: none; background-color: #fff;}blockquote { margin-bottom: 1rem; font-size: 1.25rem; } ");
		contenidoCorreo.append("</style>");
		contenidoCorreo.append("<meta charset=\"UTF-8\">");
		contenidoCorreo.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		contenidoCorreo.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		contenidoCorreo.append("</head>");
		contenidoCorreo.append("<body>");
		contenidoCorreo.append("<div class=\"mx-4 mt-4\">");
		contenidoCorreo.append("<div class=\"card\">");
		contenidoCorreo.append("<div class=\"\">");
		contenidoCorreo.append(
				"<h2 style=\"color: #253E5C; font-size: calc(1.325rem + .9vw); text-align: center!important;\" class=\"text-center\">¿Problemas para acceder?</h2>");
		contenidoCorreo.append("</div>");
		contenidoCorreo.append("<div class=\"card-body\">");
		contenidoCorreo.append("<h5 class=\"card-title\" style=\"color: #253E5C;\">Estimad@ ").append(nombreUsuario)
				.append(" recibimos una solicitud para restablecer tu contraseña de AdoptaMe, asociada con ")
				.append(email).append("</h5>");
		contenidoCorreo.append(
				"<h5 class=\"mt-3\" style=\"color: #74422B; font-size: 1.25rem; margin-bottom: 0.5rem; font-weight: 500; line-height: 1.2; \"> Datos para acceder a su cuenta.</h5>");
		contenidoCorreo.append("<div class=\"card\" style=\"box-sizing: border-box;\">");
		contenidoCorreo.append(" <ul class=\"list-group list-group-flush\">");
		contenidoCorreo.append(" <li class=\"list-group-item\">");
		contenidoCorreo.append("<p>Correo:</p>").append(email);
		contenidoCorreo.append("</li>");
		contenidoCorreo.append("<li class=\"list-group-item\">");
		contenidoCorreo.append("<p>Contraseña:</p>").append(contrasena);
		contenidoCorreo.append("</li>");
		contenidoCorreo.append("</ul>");
		contenidoCorreo.append("</div>");
		contenidoCorreo.append("<blockquote class=\"blockquote mb-0\">");
		contenidoCorreo.append(
				"<h5 class=\"mt-3\" style=\"color: #253E5C;\">Ingresa con los datos proporcionados y no olvides cambiar la contraseña por una de tu preferencia</h5>");
		contenidoCorreo.append(
				"<h5 style=\"color: #253E5C;\">Si no realizó esta solicitud, contáctenos...</h5>");
		contenidoCorreo.append(" </blockquote>");
		contenidoCorreo.append(" <center>© 2022 Copyright: <br> AdoptaMe.com</center>");
		contenidoCorreo.append(" </div>");
		contenidoCorreo.append(" </div>");
		contenidoCorreo.append(" </div>");
		contenidoCorreo.append("</body>");
		contenidoCorreo.append("</html>");

		return contenidoCorreo.toString();
	}

}
