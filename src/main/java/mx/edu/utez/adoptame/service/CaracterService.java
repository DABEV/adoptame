package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Caracter;

public interface CaracterService {
    List<Caracter> listarCaracteres();
    Caracter obtenerCaracter(Long id);
}
