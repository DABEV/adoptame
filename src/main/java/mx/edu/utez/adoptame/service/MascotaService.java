package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;

public interface MascotaService {
    List<Mascota> listarMascotas();

    List<Mascota> listarMascotas(boolean tipoMascota);

    Mascota guardarMascota(Mascota mascota);

    Mascota obtenerMascota(Long id);

    boolean eliminarMascota(Long id, HttpSession session);

    List<Mascota> filtrarPorParametros(Color color, boolean sexo, Tamano tamano);

    List<Mascota> obtenerRecientes();

    Mascota validarRegistro(long id, String verificado);

    List<Mascota> procedimientoRegistrarMascota(Long idUsuario, String aprobadoRegistro, String detalles, String edad,
            Date fechaRegistro, String imagen, String nombre, Boolean sexo, Boolean tipo, Long caracterId,
            Long colorId, Long tamanoId, Boolean activo, Boolean disponibleAdopcion);

    List<Mascota> procedimientoActualizarMascota(String aprobadoRegistroAnterior,
            String detallesAnterior, String edadAnterior,
            Date fechaRegistroAnterior, String imagenAnterior, String nombreAnterior, Boolean sexoAnterior,
            Boolean tipoAnterior, Long caracterIdAnterior,
            Long colorIdAnterior, Long tamanoIdAnterior, Boolean activoAnterior, Boolean disponibleAdopcionAnterior,
            String aprobadoRegistro, String detalles, String edad,
            Date fechaRegistro, String imagen, String nombre, Boolean sexo, Boolean tipo, Long caracterId,
            Long colorId, Long tamanoId, Boolean activo, Boolean disponibleAdopcion, HttpSession session);

    List<Mascota> procedimientoEliminarMascota(Long idUsuario, String aprobadoRegistro, String detalles, String edad,
            Date fechaRegistro, String imagen, String nombre, Boolean sexo, Boolean tipo, Long caracterId,
            Long colorId, Long tamanoId, Boolean activo, Boolean disponibleAdopcion);
}
