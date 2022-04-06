package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Mascota;

public interface MascotaService {
    List<Mascota> listarMascotas();
    Mascota guardarMascota(Mascota mascota);
    Mascota actualizarMascota (Mascota mascota);
    Mascota obtenerMascota(Long id);
    boolean eliminarMascota(Long id);
    
    boolean validarRegistro(Mascota mascota);
}
