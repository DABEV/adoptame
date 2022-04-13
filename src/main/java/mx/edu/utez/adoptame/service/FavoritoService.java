package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.model.Usuario;

public interface FavoritoService {
    List<Favorito> listarFavoritos(Usuario usuario);
    Favorito guardarFavorito(Favorito favorito);
    Favorito obtenerFavorito(Long id);
    boolean eliminarFavorito(Long id);
}
