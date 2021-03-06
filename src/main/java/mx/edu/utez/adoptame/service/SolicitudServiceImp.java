package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.repository.SolicitudRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class SolicitudServiceImp implements SolicitudService {

    Log log = LogFactory.getLog(getClass());

    @Autowired
    SolicitudRepository repository;

    private String usuarioSession = "usuario";

    @Override
    public List<Solicitud> listarSolicitudAdoptador(long idUsuario) {
        return repository.findByAdoptadorId(idUsuario);
    }

    @Override
    public List<Solicitud> listarUsuarioSolicitud(long idMascota, long idUsuario) {
        return repository.findByMascotaIdAndAdoptadorId(idMascota, idUsuario);
    }

    @Override
    public List<Solicitud> listarSolicitudes() {
        return repository.findAll();
    }

    @Override
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        Solicitud solicitudResultado = null;
        try {
            solicitudResultado = repository.save(solicitud);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return solicitudResultado;
    }

    @Override
    public Solicitud obtenerSolicitud(Long id) {
        Solicitud solicitudResultado = null;
        Optional<Solicitud> sOptional = repository.findById(id);

        if (sOptional.isPresent()) {
            solicitudResultado = sOptional.get();
        }

        return solicitudResultado;
    }

    @Override
    public boolean eliminarSolicitud(Long id, HttpSession session) {
        try {
            Solicitud solicitud = repository.getById(id);
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            Long idUsuario = usuarioDto.getId();
            procedimientoEliminarSolicitud(idUsuario, solicitud.getAprobado(), solicitud.getFechaSolicitud(),
                    solicitud.getAdoptador().getId(), solicitud.getMascota().getId());
            repository.deleteById(id);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean rechazarSolicitud(Long id) {
        try {
            repository.update("Rechazado", id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean aprobarSolicitud(Long id) {
        try {
            repository.update("Aprobado", id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Solicitud> procedimientoRegistrarSolicitud(Long idUsuario, String aprobado, Date fechaSolicitud,
            Long adoptadorId, Long mascotaId) {
        List<Solicitud> list = new ArrayList<>();

        try {

            list = repository.registroSolicitud(idUsuario, aprobado, fechaSolicitud, adoptadorId, mascotaId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Solicitud> procedimientoActualizarSolicitud(Long idUsuario, Solicitud anterior, Solicitud solicitud) {
        List<Solicitud> list = new ArrayList<>();

        try {

            list = repository.actualizarSolicitud(idUsuario, anterior, solicitud);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Solicitud> procedimientoEliminarSolicitud(Long idUsuario, String aprobado, Date fechaSolicitud,
            Long adoptadorId, Long mascotaId) {
        List<Solicitud> list = new ArrayList<>();

        try {
            list = repository.eliminarSolicitud(idUsuario, aprobado, fechaSolicitud, adoptadorId, mascotaId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

}
