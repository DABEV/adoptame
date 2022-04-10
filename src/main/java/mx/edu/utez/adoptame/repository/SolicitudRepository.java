package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import mx.edu.utez.adoptame.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    @Modifying
    @Query(value = "update solicitudes s set s.aprobado = ? where s.id = ?", nativeQuery = true)
    int updateSolicitudesSetAprobadoForIdNative(String aprobado, long id);

}
