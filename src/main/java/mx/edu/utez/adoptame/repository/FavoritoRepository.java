package mx.edu.utez.adoptame.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long>{
    List<Favorito> findByUsuarioId(long idUsuario);
    Optional<Favorito> findByMascotaIdAndUsuarioId(long idMascota, long idUsuario);
}
