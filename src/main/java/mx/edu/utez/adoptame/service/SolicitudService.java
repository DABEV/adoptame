package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.edu.utez.adoptame.model.Solicitud;

public interface SolicitudService {
    List<Solicitud> listarSolicitudAdoptador(long idUsuario);
    List<Solicitud> listarUsuarioSolicitud(long idMascota, long idUsuario);
    List<Solicitud> listarSolicitudes ();
    Solicitud guardarSolicitud (Solicitud solicitud);
    Solicitud obtenerSolicitud (Long id);

    boolean eliminarSolicitud(Long id, HttpSession session);

    boolean rechazarSolicitud(Long id);

    boolean aprobarSolicitud(Long id);

    List<Solicitud> procedimientoRegistrarSolicitud(Long idUsuario, String aprobado, Date fechaSolicitud,
            Long adoptadorId,
            Long mascotaId);

    List<Solicitud> procedimientoActualizarSolicitud(Long idUsuario, String aprobadoAnterior,
            Date fechaSolicitudAnterior,
            Long adoptadorIdAnterior,
            Long mascotaIdAnterior, String aprobado, Date fechaSolicitud,
            Long adoptadorId,
            Long mascotaId);

    List<Solicitud> procedimientoEliminarSolicitud(Long idUsuario, String aprobado, Date fechaSolicitud,
            Long adoptadorId,
            Long mascotaId);
}