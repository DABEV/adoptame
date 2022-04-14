package mx.edu.utez.adoptame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.SolicitudServiceImp;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    private String redirectAdoptador = "redirect:/solicitud/adoptador/consultarTodas";
    private String redirectVoluntario = "redirect:/solicitud/voluntario/consultarTodas";

    @Autowired
    SolicitudServiceImp solicitudServiceImp;

    @GetMapping("/adoptador/consultarTodas")
    public String solicitudesAdoptador(Model model) {
        model.addAttribute("listaSolicitud", solicitudServiceImp.listarSolicitudes());
        return "solicitud/adoptador";
    }

    @GetMapping("/adoptador/consultaUnica/{id}")
    public String solicitudAdoptadorUnico(@PathVariable long id, Model model) {
        try {
            Solicitud solicitud = solicitudServiceImp.obtenerSolicitud(id);
            if (solicitud != null) {
                model.addAttribute("listaSolicitud", solicitud);
                return "solicitud/adoptador";
            }
        } catch (Exception e) {
            // log
        }
        return redirectAdoptador;
    }

    @GetMapping("/adoptador/eliminarSolicitud/{id}")
    public String eliminarSolicitudAdoptador(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            boolean respuesta = solicitudServiceImp.eliminarSolicitud(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Solicitud eliminada de manera exitosa");
                return redirectAdoptador;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Eliminación de solicitud fallida");
            }
        } catch (Exception e) {
            // log
        }
        return redirectAdoptador;
    }

    @PostMapping("/adoptador/guardarSolicitud")
    public String guardarSolicitud(Solicitud solicitud, Mascota mascota, Usuario usuario, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            if (solicitud.getId() == null) {
                solicitud.setAprobado("Pendiente");
                solicitud.setAdoptador(usuario);
                solicitud.setMascota(mascota);
            }

            Solicitud respuesta = solicitudServiceImp.guardarSolicitud(solicitud);
            if (respuesta != null) {
                redirectAttributes.addFlashAttribute("msg_success", "Registro de solicitud exitoso");
                return redirectAdoptador;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro de solicitud fallido");
                // return "mascota/detallesMascota"
                return redirectAdoptador;
            }
        } catch (Exception e) {
            // log
        }
        return redirectAdoptador;
    }

    @GetMapping("/voluntario/consultarTodas")
    public String solicitudesVoluntario(Model model) {
        model.addAttribute("listaSolicitud", solicitudServiceImp.listarSolicitudes());
        return "solicitud/voluntario";
    }

    @GetMapping("/voluntario/consultaUnica/{id}")
    public String solicitudVoluntarioUnico(@PathVariable long id, Model model) {
        try {
            Solicitud solicitud = solicitudServiceImp.obtenerSolicitud(id);
            if (solicitud != null) {
                model.addAttribute("solicitud", solicitud);
                return "solicitud/voluntario";
            }
        } catch (Exception e) {
            // log
        }

        return redirectVoluntario;
    }

    @GetMapping("/voluntario/rechazarSolicitud/{id}")
    public String rechazarSolicitud(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            boolean respuesta = solicitudServiceImp.rechazarSolicitud(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Solicitud rechazada exitosamente");
                return redirectVoluntario;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Rechazo de solicitud fallida");
            }
        } catch (Exception e) {
            // log
        }
        return redirectVoluntario;
    }

    @GetMapping("/voluntario/aprobarSolicitud/{id}")
    public String aprobarSolicitud(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            boolean respuesta = solicitudServiceImp.aprobarSolicitud(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Solicitud aprobada exitosamente");
                return redirectVoluntario;
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Aprobación de solicitud fallida");
            }
        } catch (Exception e) {
            //log
        }
        return redirectVoluntario;
    }

}
