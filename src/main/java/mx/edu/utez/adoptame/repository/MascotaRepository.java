package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Mascota;

public interface MascotaRepository  extends JpaRepository<Mascota, Long> {
    
}