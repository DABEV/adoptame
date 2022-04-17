package mx.edu.utez.adoptame.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.service.SolicitudServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    private String redirectAdoptador = "redirect:/solicitud/adoptador/consultarTodas";
    private String redirectVoluntario = "redirect:/solicitud/voluntario/consultarTodas";
    private String msgE = "msg_error";
    private String msgS = "msg_success";
    private String msgW = "msg_warning";
    private String listaSolicitud = "listaSolicitud";

    private Logger logger = LoggerFactory.getLogger(SolicitudController.class);

    @Autowired
    SolicitudServiceImp solicitudServiceImp;

    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    @Autowired
    MascotaServiceImp mascotaServiceImp;

    @GetMapping("/adoptador/consultarTodas")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String solicitudesAdoptador(Authentication authentication, Model model) {
        try {
            String correo = authentication.getName();
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(correo);

            List<Solicitud> solicitados = solicitudServiceImp.listarSolicitudAdoptador(usuario.getId());

            model.addAttribute(listaSolicitud, solicitados);
        } catch (Exception e) {
            logger.error("Error al listar las solicitudes");
        }
        return "solicitud/adoptador";
    }

    @GetMapping("/adoptador/consultaUnica/{id}")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String solicitudAdoptadorUnico(@PathVariable long id, Model model) {
        try {
            Solicitud solicitud = solicitudServiceImp.obtenerSolicitud(id);
            if (solicitud != null) {
                model.addAttribute(listaSolicitud, solicitud);
                return "solicitud/adoptador";
            }
        } catch (Exception e) {
            logger.error("Error al intentar listar las solicitudes");
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
                redirectAttributes.addFlashAttribute(msgS, "Solicitud eliminada de manera exitosa");
                return redirectAdoptador;
            } else {
                redirectAttributes.addFlashAttribute(msgE, "Eliminación de solicitud fallida");
            }
        } catch (Exception e) {
            logger.error("Error al intentar eliminar la solicitud");
        }
        return redirectAdoptador;
    }

    @PostMapping("/adoptador/guardarSolicitud")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String guardarSolicitud(@RequestParam("idMascota") long id, Authentication authentication, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        Solicitud respuesta = null;
        Solicitud soli = new Solicitud();

        try {
            String correo = authentication.getName();
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(correo);
            Mascota mascota = mascotaServiceImp.obtenerMascota(id);
            List<Solicitud> solicitados = solicitudServiceImp.listarUsuarioSolicitud(id, usuario.getId());
            if (solicitados.isEmpty() && mascota.getAprobadoRegistro().equals("aprobado")
                    && Boolean.TRUE.equals(mascota.getDisponibleAdopcion())) {
                soli.setAprobado("Pendiente");
                soli.setAdoptador(usuario);
                soli.setMascota(mascota);
            } else if (Boolean.FALSE.equals(mascota.getDisponibleAdopcion())) {
                redirectAttributes.addFlashAttribute(msgW, "Esta mascota ya no se encuentra disponible para adopción");
                if (Boolean.FALSE.equals(mascota.getSexo())) {
                    return "redirect:/mascota/consultarTodas/false";
                } else {
                    return "redirect:/mascota/consultarTodas/true";
                }
            } else {
                redirectAttributes.addFlashAttribute(msgW, "Ya has solicitado esta mascota");
                if (Boolean.FALSE.equals(mascota.getSexo())) {
                    return "redirect:/mascota/consultarTodas/false";
                } else {
                    return "redirect:/mascota/consultarTodas/true";
                }
            }

            respuesta = solicitudServiceImp.guardarSolicitud(soli);

            if (respuesta != null) { 
                System.out.println(usuario.getId() + " " + respuesta.getAprobado() + " "+
                respuesta.getFechaSolicitud() + " " + respuesta.getAdoptador().getId() + " " + respuesta.getMascota().getId());
                solicitudServiceImp.procedimientoRegistrarSolicitud(usuario.getId(), respuesta.getAprobado(),
                        respuesta.getFechaSolicitud(), respuesta.getAdoptador().getId(), respuesta.getMascota().getId());
                redirectAttributes.addFlashAttribute(msgS, "Registro de solicitud exitoso");
                return redirectAdoptador;
            } else {
                redirectAttributes.addFlashAttribute(msgE, "Registro de solicitud fallido");
                return redirectAdoptador;
            }
        } catch (Exception e) {
            logger.error("Error al intentar registrar una solicitud de adopción");
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
            logger.error("Error al intentar listar las solicitudes");
        }

        return redirectVoluntario;
    }

    @GetMapping("/voluntario/rechazarSolicitud/{id}")
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
    public String rechazarSolicitud(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            Solicitud solicitud = solicitudServiceImp.obtenerSolicitud(id);

            if(solicitud.getAprobado().equals("Aprobado")){
                redirectAttributes.addFlashAttribute(msgW, "No puedes rechazar una solicitud que ya fue aprobada");
                return redirectVoluntario;
            }
            boolean respuesta = solicitudServiceImp.rechazarSolicitud(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute(msgS, "Solicitud rechazada exitosamente");
                return redirectVoluntario;
            } else {
                redirectAttributes.addFlashAttribute(msgE, "Rechazo de solicitud fallida");
            }
        } catch (Exception e) {
            logger.error("Error al intentar rechazar una solicitud de adopción");
        }
        return redirectVoluntario;
    }

    @GetMapping("/voluntario/aprobarSolicitud/{id}")
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
    public String aprobarSolicitud(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            Solicitud solicitud = solicitudServiceImp.obtenerSolicitud(id);
            Mascota mascota = mascotaServiceImp.obtenerMascota(solicitud.getMascota().getId());
            if(solicitud.getAprobado().equals("Aprobado") && Boolean.FALSE.equals(mascota.getDisponibleAdopcion())){
                redirectAttributes.addFlashAttribute(msgW, "Esta solicitud ya fue aprobada");
                return redirectVoluntario;
            }else if(Boolean.FALSE.equals(mascota.getDisponibleAdopcion())){
                redirectAttributes.addFlashAttribute(msgW, "Esta mascota ya no está disponible para ser adoptada");
                return redirectVoluntario;
            }else{
                Mascota mascotaExistente = mascotaServiceImp.obtenerMascota(mascota.getId());
                    mascota.setFechaRegistro(mascotaExistente.getFechaRegistro());
                    mascota.setDisponibleAdopcion(false);
                    mascota.setAprobadoRegistro(mascotaExistente.getAprobadoRegistro());
                    mascota.setActivo(mascotaExistente.getActivo());

                Mascota actualizar = mascotaServiceImp.guardarMascota(mascota);
                boolean respuesta = solicitudServiceImp.aprobarSolicitud(id);
                if (respuesta && actualizar != null) {
                    redirectAttributes.addFlashAttribute(msgS, "Solicitud aprobada exitosamente");
                    return redirectVoluntario;
                } else {
                    redirectAttributes.addFlashAttribute(msgE, "Aprobación de solicitud fallida");
                }
            }
        } catch (Exception e) {
            logger.error("Error al intentar aprobar una solicitud de adopción");
        }
        return redirectVoluntario;
    }

}
