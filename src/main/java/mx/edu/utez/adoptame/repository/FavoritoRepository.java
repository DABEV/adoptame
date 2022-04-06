package mx.edu.utez.adoptame.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long>{
    
}
