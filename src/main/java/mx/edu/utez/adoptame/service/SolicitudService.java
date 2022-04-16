package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Solicitud;

public interface SolicitudService {
    List<Solicitud> listarSolicitudAdoptador(long idUsuario);
    List<Solicitud> listarSolicitudes ();
    Solicitud guardarSolicitud (Solicitud solicitud);
    Solicitud obtenerSolicitud (Long id);

    boolean eliminarSolicitud (Long id);
    boolean rechazarSolicitud(Long id);
    boolean aprobarSolicitud(Long id);
}
