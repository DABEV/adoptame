package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Tamano;

public interface TamanoService {
    List<Tamano> listarTamanos();
    Tamano guardarTamano(Tamano tamano);
    Tamano obtenerTamano(Long id);
    boolean eliminarTamano(long id);
}
