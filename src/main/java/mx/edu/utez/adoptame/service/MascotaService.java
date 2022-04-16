package mx.edu.utez.adoptame.service;

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
    List<Mascota> filtrarPorParametros(Color color, boolean sexo, Tamano tamano, boolean tipoMascota);
    List<Mascota> obtenerRecientes();
    List<Mascota> obtenerPendientes();
    Mascota validarRegistro(long id, String verificado);

    List<Mascota> procedimientoRegistrarMascota(Long idUsuario,Mascota mascota);

    List<Mascota> procedimientoActualizarMascota(Mascota mascota, Mascota anterior, HttpSession session);

    List<Mascota> procedimientoEliminarMascota(Long idUsuario, Mascota mascota);
}
