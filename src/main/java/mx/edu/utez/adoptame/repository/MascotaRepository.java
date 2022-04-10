package mx.edu.utez.adoptame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Mascota;

public interface MascotaRepository  extends JpaRepository<Mascota, Long> {
    List<Mascota> findByActivo(Boolean activo); 
}
