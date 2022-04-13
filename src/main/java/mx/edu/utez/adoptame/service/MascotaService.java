package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;

public interface MascotaService {
    List<Mascota> listarMascotas();
    List<Mascota> listarMascotas(boolean tipoMascota);
    Mascota guardarMascota(Mascota mascota);
    Mascota obtenerMascota(Long id);
    boolean eliminarMascota(Long id);
    List<Mascota> filtrarPorParametros(Color color, boolean sexo, Tamano tamano);
    
    boolean validarRegistro(Mascota mascota);
}
