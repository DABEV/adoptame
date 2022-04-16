package mx.edu.utez.adoptame.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.SolicitudServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    private String redirectAdoptador = "redirect:/solicitud/adoptador/consultarTodas";
    private String redirectVoluntario = "redirect:/solicitud/voluntario/consultarTodas";
    private String listaSolicitud = "solicitud/adoptador/consultarTodas";

    @Autowired
    SolicitudServiceImp solicitudServiceImp;

    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    @GetMapping("/adoptador/consultarTodas")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String solicitudesAdoptador(Authentication authentication, Model model) {
        try {
            String correo = authentication.getName();
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(correo);

            List<Solicitud> solicitados = solicitudServiceImp.listarSolicitudAdoptador(usuario.getId());

            model.addAttribute("listaSolicitud", solicitados);
        } catch (Exception e) {
            // log
        }
        return "solicitud/adoptador";
    }

    @GetMapping("/adoptador/consultaUnica/{id}")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
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
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String eliminarSolicitudAdoptador(@PathVariable long id, RedirectAttributes redirectAttributes,
            HttpSession session) {
        try {
            boolean respuesta = solicitudServiceImp.eliminarSolicitud(id, session);
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
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String guardarSolicitud(Solicitud solicitud, Mascota mascota, Usuario usuario, Model model,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Solicitud respuesta = null;
        try {
            if (solicitud.getId() == null) {
                solicitud.setAprobado("Pendiente");
                solicitud.setAdoptador(usuario);
                solicitud.setMascota(mascota);
            } else {
                Solicitud solicitudAnterior = solicitudServiceImp.obtenerSolicitud(solicitud.getId());
                UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute("usuario");
                Long idUsuario = usuarioDto.getId();

                solicitudServiceImp.procedimientoActualizarSolicitud(idUsuario, solicitudAnterior.getAprobado(),
                        solicitudAnterior.getFechaSolicitud(), solicitudAnterior.getAdoptador().getId(),
                        solicitudAnterior.getMascota().getId(),
                        solicitud.getAprobado(), solicitud.getFechaSolicitud(), solicitud.getAdoptador().getId(),
                        solicitud.getMascota().getId());
                respuesta = solicitudServiceImp.guardarSolicitud(solicitud);

            }

            respuesta = solicitudServiceImp.guardarSolicitud(solicitud);
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
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
    public String solicitudesVoluntario(Model model) {
        model.addAttribute("listaSolicitud", solicitudServiceImp.listarSolicitudes());
        return "solicitud/voluntario";
    }

    @GetMapping("/voluntario/consultaUnica/{id}")
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
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
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
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
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
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
            // log
        }
        return redirectVoluntario;
    }

}
