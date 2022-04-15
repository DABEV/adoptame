package mx.edu.utez.adoptame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;

public interface MascotaRepository  extends JpaRepository<Mascota, Long> {
    List<Mascota> findByActivo(Boolean activo);
    List<Mascota> findByActivoAndTipo(Boolean activo, boolean tipo);
    List<Mascota> findByColorOrSexoOrTamano(Color color, boolean sexo, Tamano tamano);
    List<Mascota> findByAprobadoRegistro(String aprobadoRegistro);

    @Query(value = "select * from mascotas order by fecha_registro limit 3", nativeQuery = true)
    List<Mascota> obtenerRecientes();
}
