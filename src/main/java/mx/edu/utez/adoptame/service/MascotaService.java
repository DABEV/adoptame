package mx.edu.utez.adoptame.service;

import java.util.List;

public interface MascotaService {
    List<Mascota> listarMascotas();
    Mascota guardarMascota(Mascota mascota);
    Mascota actualizarMascota (Mascota mascota);
    Mascota obtenerMascota(long id);
    boolean eliminarMascota(long id);
    
    boolean validarRegistro(Mascota mascota);
}
