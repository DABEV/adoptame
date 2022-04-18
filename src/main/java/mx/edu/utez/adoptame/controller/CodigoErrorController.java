package mx.edu.utez.adoptame.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mx.edu.utez.adoptame.model.Respuesta;

@Controller
public class CodigoErrorController implements ErrorController {

    // Valores de error default
    private static final Integer DEFAULT_STATUS_01 = 600;
    private static final String DEFAULT_TITLE_01 = "¡Ha ocurrido un error!";
    private static final String DEFAULT_MESSAGE_01 = "Favor de contactar a un administrador.";

    @GetMapping("/error")
    public String handleError (HttpServletRequest request, Model model) {        
        model.addAttribute("respuesta", setRespuestaError(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)));
        return "error";
    }

    private Respuesta setRespuestaError (Object estatusObj) {
        Integer estatus;
        String titulo;
        String mensaje;

        estatus = estatusObj != null ? Integer.valueOf(estatusObj.toString()) : DEFAULT_STATUS_01;
        
        if (estatus == HttpStatus.NOT_FOUND.value()) {
            titulo = "¡Página no encontrada!";
            mensaje = "Lo siento, pero lo que estás buscando no se encuentra por aquí.";
        } else if (estatus == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            titulo = "¡Error en el servidor!";
            mensaje = "Ha ocurrido un error, favor de contactar al administrador.";
        } else if (estatus == HttpStatus.FORBIDDEN.value()) {
            titulo = "¡Sin permisos necesarios!";
            mensaje = "No puedes acceder a este sitio, no cuentas con los permisos necesarios.";
        } else {
            titulo = DEFAULT_TITLE_01;
            mensaje = DEFAULT_MESSAGE_01;
        }

        return new Respuesta(estatus, titulo, mensaje);
    }
}
