package mx.edu.utez.adoptame.repository;

import java.util.Date;
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

    @Query(value= "select * FROM mascotas m JOIN colores cl ON cl.id = m.color_id JOIN tamanos t ON t.id = m.tamano_id JOIN caracteres cr ON cr.id = m.caracter_id where (m.nombre like %:texto% or cl.nombre like %:texto% or t.nombre like %:texto% or  cr.nombre like %:texto%) and m.activo= 1 and m.aprobado_registro = 'aprobado' and m.disponible_adopcion = 1 and m.tipo = :tipoMascota", nativeQuery = true)
    List<Mascota> filtroPlabrasClave(@Param("texto") String texto, @Param("tipoMascota") boolean tipoMascota);

    @Query(value = "{call registroMascota(:usuario_id, :aprobadoRegistro, :detalles, :edad, :fechaRegistro, :imagen, :nombre, :sexo, :tipo, :caracterId, :colorId, :tamanoId, :activo, :disponibleAdopcion) }", nativeQuery = true)
    List<Mascota> registroMascota(@Param("usuario_id") Long idUsuario,
            @Param("aprobadoRegistro") String aprobadoRegistro, @Param("detalles") String detalles,
            @Param("edad") String edad, @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen,
            @Param("nombre") String nombre, @Param("sexo") Boolean sexo, @Param("tipo") Boolean tipo,
            @Param("caracterId") Long caracterId,
            @Param("colorId") Long colorId, @Param("tamanoId") Long tamanoId, @Param("activo") Boolean activo,
            @Param("disponibleAdopcion") Boolean disponibleAdopcion);

    @Query(value = "{call actualizarMascota(:usuario_id, :aprobadoRegistroAnterior, :detallesAnterior, :edadAnterior, :fechaRegistroAnterior, :imagenAnterior, :nombreAnterior, :sexoAnterior, :tipoAnterior, :caracterIdAnterior, :colorIdAnterior, :tamanoIdAnterior, :activoAnterior, :disponibleAdopcionAnterior, :aprobadoRegistro, :detalles, :edad, :fechaRegistro, :imagen, :nombre, :sexo, :tipo, :caracterId, :colorId, :tamanoId, :activo, :disponibleAdopcion ) }", nativeQuery = true)
    List<Mascota> actualizarMascota(@Param("usuario_id") Long idUsuario,
            @Param("aprobadoRegistroAnterior") String aprobadoRegistroAnterior,
            @Param("detallesAnterior") String detallesAnterior,
            @Param("edadAnterior") String edadAnterior, @Param("fechaRegistroAnterior") Date fechaRegistroAnterior,
            @Param("imagenAnterior") String imagenAnterior,
            @Param("nombreAnterior") String nombreAnterior, @Param("sexoAnterior") Boolean sexoAnterior,
            @Param("tipoAnterior") Boolean tipoAnteriorAnterior,
            @Param("caracterIdAnterior") Long caracterIdAnterior,
            @Param("colorIdAnterior") Long colorIdAnterior, @Param("tamanoIdAnterior") Long tamanoIdAnterior,
            @Param("activoAnterior") Boolean activoAnterior,
            @Param("disponibleAdopcionAnterior") Boolean disponibleAdopcionAnterior,
            @Param("aprobadoRegistro") String aprobadoRegistro, @Param("detalles") String detalles,
            @Param("edad") String edad, @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen,
            @Param("nombre") String nombre, @Param("sexo") Boolean sexo, @Param("tipo") Boolean tipo,
            @Param("caracterId") Long caracterId,
            @Param("colorId") Long colorId, @Param("tamanoId") Long tamanoId, @Param("activo") Boolean activo,
            @Param("disponibleAdopcion") Boolean disponibleAdopcion);

            @Query(value = "{call eliminarMascota(:usuario_id, :aprobadoRegistro, :detalles, :edad, :fechaRegistro, :imagen, :nombre, :sexo, :tipo, :caracterId, :colorId, :tamanoId, :activo, :disponibleAdopcion) }", nativeQuery = true)
            List<Mascota> eliminarMascota(@Param("usuario_id") Long idUsuario,
                    @Param("aprobadoRegistro") String aprobadoRegistro, @Param("detalles") String detalles,
                    @Param("edad") String edad, @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen,
                    @Param("nombre") String nombre, @Param("sexo") Boolean sexo, @Param("tipo") Boolean tipo,
                    @Param("caracterId") Long caracterId,
                    @Param("colorId") Long colorId, @Param("tamanoId") Long tamanoId, @Param("activo") Boolean activo,
                    @Param("disponibleAdopcion") Boolean disponibleAdopcion);
        

}
