package mx.edu.utez.adoptame.service;

public interface FavoritoService {
    List<Favorito> listarFavoritos();
    Favorito guardarFavorito(Favorito favorito);
    Favorito actualizarFavorito(Favorito favorito);
    Favorito obtenerFavorito(long id);
    boolean eliminarFavorito(long id);
}
