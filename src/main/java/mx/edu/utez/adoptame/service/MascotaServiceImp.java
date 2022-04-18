package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.repository.MascotaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MascotaServiceImp implements MascotaService {

    private Logger logger = LoggerFactory.getLogger(MascotaServiceImp.class);

    private String aprobado = "aprobado";

    @Autowired
    MascotaRepository mascotaRepository;

    private Long idUsuario;
    private String usuarioSession = "usuario";

    @Override
    public List<Mascota> listarMascotas(boolean tipoMascota) {
        return mascotaRepository.findByActivoAndTipoAndAprobadoRegistroAndDisponibleAdopcion(true, tipoMascota,
                aprobado, true);
    }

    @Override
    public List<Mascota> listarMascotas() {
        List<Mascota> mascotas = null;
        try {
            mascotas = mascotaRepository.findByActivoAndAprobadoRegistroAndDisponibleAdopcion(true, aprobado, true);
        } catch (Exception e) {
            logger.error("hubo un error al intentar listar una mascota");
        }
        return mascotas;

    }

    @Override
    public Mascota guardarMascota(Mascota mascota) {
        Mascota mascotaResultante = null;
        try {

            mascotaResultante = mascotaRepository.save(mascota);

        } catch (Exception e) {
            logger.error("Error al intentar guardar una mascota");
        }
        return mascotaResultante;
    }

    @Override
    public Mascota obtenerMascota(Long id) {
        Mascota mascotaResultante = null;
        try {
            Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
            if (mascotaOpcional.isPresent()) {
                mascotaResultante = mascotaOpcional.get();
            }
        } catch (Exception e) {
            logger.error("Error al intentar obtener una mascota");
        }
        return mascotaResultante;
    }

    @Override
    public boolean eliminarMascota(Long id, HttpSession session) {
        try {
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();
            Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
            if (mascotaOpcional.isPresent()) {

                procedimientoEliminarMascota(idUsuario, mascotaOpcional.get());

                mascotaOpcional.get().setActivo(false);
                mascotaRepository.save(mascotaOpcional.get());
                return true;
            }
        } catch (Exception e) {
            logger.error("Error al intentar eliminar una mascota");
        }
        return false;
    }

    @Override
    public Mascota validarRegistro(long id, String verificado) {
        Mascota mascota = null;
        try {
            Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
            if (mascotaOpcional.isPresent()) {
                mascota = mascotaOpcional.get();
                mascota.setAprobadoRegistro(verificado);
                mascotaRepository.save(mascota);
            }
        } catch (Exception e) {
            logger.error("Error al intentar validar una mascota");
        }
        return mascota;
    }

    @Override
    public List<Mascota> filtrarPorParametros(Color color, boolean sexo, Tamano tamano, boolean tipoMascota) {
        List<Mascota> mascotas = new ArrayList<>();
        try {
            List<Mascota> activos = mascotaRepository.findByActivoAndTipoAndAprobadoRegistroAndDisponibleAdopcion(true,
                    tipoMascota, aprobado, true);
            List<Mascota> parametros = mascotaRepository.findByColorOrSexoOrTamano(color, sexo, tamano);

            for (Mascota m : parametros) {
                if (activos.contains(m)) {
                    mascotas.add(m);
                }
            }

        } catch (Exception e) {
            logger.error("Error al intentar filtrar una mascota");
        }
        return mascotas;
    }

    @Override
    public List<Mascota> obtenerRecientes() {
        List<Mascota> mascotas = new ArrayList<>();
        try {
            mascotas = mascotaRepository.obtenerRecientes();
        } catch (Exception e) {
            logger.error("Error al intentar obtener las mascotas recientes");
        }
        return mascotas;
    }

    @Override
    public List<Mascota> obtenerPendientes() {
        List<Mascota> mascotas = new ArrayList<>();
        try {
            mascotas = mascotaRepository.findByAprobadoRegistro("pendiente");
        } catch (Exception e) {
            logger.error("Error al intentar obtener las mascotas pendientes de aprobación");
        }
        return mascotas;
    }

    @Override
    public List<Mascota> procedimientoRegistrarMascota(Long idUsuario, Mascota mascota) {
        List<Mascota> mascotas = new ArrayList<>();
        try {
            mascotas = mascotaRepository.registroMascota(idUsuario, mascota);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return mascotas;
    }

    @Override
    public List<Mascota> procedimientoActualizarMascota(Mascota mascota, Mascota anterior, HttpSession session) {
        List<Mascota> mascotas = new ArrayList<>();

        try {
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();
            mascotaRepository.actualizarMascota(idUsuario, mascota, anterior);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return mascotas;
    }

    @Override
    public List<Mascota> procedimientoEliminarMascota(Long idUsuario, Mascota mascota) {
        List<Mascota> mascotas = new ArrayList<>();

        try {
            mascotas = mascotaRepository.eliminarMascota(idUsuario, mascota);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return mascotas;
    }

}
