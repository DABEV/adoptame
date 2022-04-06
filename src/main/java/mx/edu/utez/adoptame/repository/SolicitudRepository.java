package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    
}
