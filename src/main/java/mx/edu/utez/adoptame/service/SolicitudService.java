package mx.edu.utez.adoptame.service;

public interface SolicitudService {
    List<Solicitud> listarSolicitudes ();
    Solicitud guardarSolicitud (Solicitud solicitud);
    Solicitud actualizarSolicitud (Solicitud solicitud);
    Solicitud obtenerSolicitud (long id);
    boolean eliminarSolicitud (long id);

    boolean rechazarSolicitud(long id);
    boolean aprobarSolicitud(long id);
}
