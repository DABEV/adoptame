package mx.edu.utez.adoptame.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Donacion;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    
}
