package mx.edu.utez.adoptame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
        List<Mascota> findByActivoAndAprobadoRegistroAndDisponibleAdopcion(Boolean activo, String aprobado,
                        boolean disponible);

        List<Mascota> findByActivoAndTipoAndAprobadoRegistroAndDisponibleAdopcion(Boolean activo, boolean tipo,
                        String aprobado, boolean disponible);

        List<Mascota> findByColorOrSexoOrTamano(Color color, boolean sexo, Tamano tamano);

        List<Mascota> findByAprobadoRegistro(String aprobadoRegistro);

        @Query(value = "select * from mascotas order by fecha_registro limit 3", nativeQuery = true)
        List<Mascota> obtenerRecientes();

        @Query(value = "{call registroMascota(:usuario_id, :#{#mascota.aprobadoRegistro}, :#{#mascota.detalles}, :#{#mascota.edad}, :#{#mascota.fechaRegistro}, :#{#mascota.imagen}, :#{#mascota.nombre}, :#{#mascota.sexo}, :#{#mascota.tipo}, :#{#mascota.caracter.id}, :#{#mascota.color.id}, :#{#mascota.tamano.id}, :#{#mascota.activo}, :#{#mascota.disponibleAdopcion})}", nativeQuery = true)
        List<Mascota> registroMascota(@Param("usuario_id") Long idUsuario, @Param("mascota") Mascota mascota);

        @Query(value = "{call actualizarMascota(:usuario_id, :#{#anterior.aprobadoRegistro}, :#{#anterior.detalles}, :#{#anterior.edad}, :#{#anterior.fechaRegistro}, :#{#anterior.imagen}, :#{#anterior.nombre}, :#{#anterior.sexo}, :#{#anterior.tipo}, :#{#anterior.caracter.id}, :#{#anterior.color.id}, :#{#anterior.tamano.id}, :#{#anterior.activo}, :#{#anterior.disponibleAdopcion}, :#{#mascota.aprobadoRegistro}, :#{#mascota.detalles}, :#{#mascota.edad}, :#{#mascota.fechaRegistro}, :#{#mascota.imagen}, :#{#mascota.nombre}, :#{#mascota.sexo}, :#{#mascota.tipo}, :#{#mascota.caracter.id}, :#{#mascota.color.id}, :#{#mascota.tamano.id}, :#{#mascota.activo}, :#{#mascota.disponibleAdopcion}) }", nativeQuery = true)
        List<Mascota> actualizarMascota(@Param("usuario_id") Long idUsuario, @Param("mascota") Mascota mascota,
                        @Param("anterior") Mascota anterior);

        @Query(value = "{call eliminarMascota(:usuario_id, :#{#mascota.aprobadoRegistro}, :#{#mascota.detalles}, :#{#mascota.edad}, :#{#mascota.fechaRegistro}, :#{#mascota.imagen}, :#{#mascota.nombre}, :#{#mascota.sexo}, :#{#mascota.tipo}, :#{#mascota.caracter.id}, :#{#mascota.color.id}, :#{#mascota.tamano.id}, :#{#mascota.activo}, :#{#mascota.disponibleAdopcion})}", nativeQuery = true)
        List<Mascota> eliminarMascota(@Param("usuario_id") Long idUsuario, @Param("mascota") Mascota mascota);

}
