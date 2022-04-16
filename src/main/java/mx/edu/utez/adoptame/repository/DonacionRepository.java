package mx.edu.utez.adoptame.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.adoptame.model.Donacion;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    @Query(value = "{call registroDonacion(:usuario_id, :autorizacion, :estado, :fechaDonacion, :monto, :donadorId) }", nativeQuery = true)
    List<Donacion> registroDonacion(@Param("usuario_id") long usuarioId, @Param("autorizacion") String autorizacion,
            @Param("estado") Boolean estado, @Param("fechaDonacion") Date fechaDonacion, @Param("monto") Double monto,
            @Param("donadorId") Long donadorId);

    @Query(value = "{call eliminarDonacion(:usuario_id, :autorizacion, :estado, :fechaDonacion, :monto, :donadorId) }", nativeQuery = true)
    List<Donacion> eliminarDonacion(@Param("usuario_id") long usuarioId, @Param("autorizacion") String autorizacion,
            @Param("estado") Boolean estado, @Param("fechaDonacion") Date fechaDonacion, @Param("monto") Double monto,
            @Param("donadorId") Long donadorId);

}
