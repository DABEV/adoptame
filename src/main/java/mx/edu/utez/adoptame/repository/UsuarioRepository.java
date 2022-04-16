package mx.edu.utez.adoptame.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Usuario;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);

    @Modifying
    @Query(value = "update usuarios u set u.contrasena = :contrasena where u.correo = :correo", nativeQuery = true)
    void updatePassword(@Param("contrasena") String contrasena, @Param("correo") String correo);

    @Query(value = "{call registroSesion(:usuario_id) }", nativeQuery = true)
    Usuario registroSesion(@Param("usuario_id") Long usuarioId);

    @Query(value = "{call registroCerrarSesion(:usuario_id, :fechaFin) }", nativeQuery = true)
    Usuario registroCerrarSesion(@Param("usuario_id") long usuarioId, @Param("fechaFin") Date fechaFin);
}
