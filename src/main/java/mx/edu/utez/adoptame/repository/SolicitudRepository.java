package mx.edu.utez.adoptame.repository;

import java.util.Date;
import java.util.List;

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
    void update(@Param("aprobado") String aprobado, @Param("id") long id);

    List<Solicitud> findByAdoptadorId(long idUsuario);
    List<Solicitud> findByMascotaIdAndAdoptadorId(long idMascota, long idUsuario);

    @Query(value = "{call registroSolicitud(:usuario_id, :aprobado, :fechaSolicitud, :adoptadorId, :mascotaId) }", nativeQuery = true)
    List<Solicitud> registroSolicitud(@Param("usuario_id") long usuarioId, @Param("aprobado") String aprobado,
            @Param("fechaSolicitud") Date fechaSolicitud, @Param("adoptadorId") Long adoptadorId,
            @Param("mascotaId") Long mascotaId);
 
    @Query(value = "{call actualizarSolicitud(:usuario_id, :#{#anterior.aprobadoAnterior}, :#{#anterior.fechaSolicitudAnterior}, :#{#anterior.adoptadorIdAnterior}, :#{#anterior.mascotaIdAnterior}, :#{#solicitud.aprobado}, :#{#solicitud.fechaSolicitud}, :#{#solicitud.adoptadorId}, :#{#solicitud.mascotaId}) }", nativeQuery = true)
    List<Solicitud> actualizarSolicitud(@Param("usuario_id") long usuarioId,
            @Param("anterior") Solicitud anterior,
            @Param("solicitud") Solicitud solicitud);

    @Query(value = "{call eliminarSolicitud(:usuario_id, :aprobado, :fechaSolicitud, :adoptadorId, :mascotaId) }", nativeQuery = true)
    List<Solicitud> eliminarSolicitud(@Param("usuario_id") long usuarioId, @Param("aprobado") String aprobado,
            @Param("fechaSolicitud") Date fechaSolicitud, @Param("adoptadorId") Long adoptadorId,
            @Param("mascotaId") Long mascotaId);
}
