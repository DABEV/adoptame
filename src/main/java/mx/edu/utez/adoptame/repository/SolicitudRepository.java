package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.adoptame.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
 
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update solicitudes s set s.aprobado = :aprobado where s.id = :id", nativeQuery = true)
    void update(@Param("aprobado") String aprobado,@Param("id") long id);
}
