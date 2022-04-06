package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Color;

public interface ColorService {
    List<Color> listarColores();
    Color guardarColor(Color color);
    Color obtenerColor(Long id);
    boolean eliminarColor(Long id);
}
