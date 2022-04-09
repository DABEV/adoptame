package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Tamano;

public interface TamanoService {
    List<Tamano> listAll();
    Tamano save(Tamano tamano);
    boolean delete(long id);
}
