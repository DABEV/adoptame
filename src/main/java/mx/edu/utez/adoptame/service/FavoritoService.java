package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Favorito;

public interface FavoritoService {
    List<Favorito> listarFavoritos(long idUsuario);
    Favorito guardarFavorito(Favorito favorito);
    Favorito obtenerFavorito(Long id);
    boolean eliminarFavorito(Long id);
    Favorito obtenerPorMascota(long idMascota, long idUsuario);
}
