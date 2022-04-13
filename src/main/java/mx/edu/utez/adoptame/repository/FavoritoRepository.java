package mx.edu.utez.adoptame.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.model.Usuario;

public interface FavoritoRepository extends JpaRepository<Favorito, Long>{
    List<Favorito> findByUsuario(Usuario usuario);
}
