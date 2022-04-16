package mx.edu.utez.adoptame.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "{call registroBlog(:usuario_id, :titulo, :contenido, :esPrincipal, :fechaRegistro, :imagen) }", nativeQuery = true)
    List<Blog> registroBlog(@Param("usuario_id") long usuarioId, @Param("titulo") String titulo,
            @Param("contenido") String contenido, @Param("esPrincipal") Boolean esPrincipal,
            @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen);

    @Query(value = "{call actualizarBlog(:usuario_id, :tituloAnterior, :contenidoAnterior, :esPrincipalAnterior, :fechaRegistroAnterior, :imagenAnterior,:titulo, :contenido, :esPrincipal, :fechaRegistro, :imagen ) }", nativeQuery = true)
    List<Blog> actualizarBlog(@Param("usuario_id") long usuarioId, @Param("tituloAnterior") String tituloAnterior,
            @Param("contenidoAnterior") String contenidoAnterior,
            @Param("esPrincipalAnterior") Boolean esPrincipalAnterior,
            @Param("fechaRegistroAnterior") Date fechaRegistroAnterior, @Param("imagenAnterior") String imagenAnterior,
            @Param("titulo") String titulo,
            @Param("contenido") String contenido, @Param("esPrincipal") Boolean esPrincipal,
            @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen);

    @Query(value = "{call eliminarBlog(:usuario_id, :titulo, :contenido, :esPrincipal, :fechaRegistro, :imagen) }", nativeQuery = true)
    List<Blog> eliminarBlog(@Param("usuario_id") long usuarioId, @Param("titulo") String titulo,
            @Param("contenido") String contenido, @Param("esPrincipal") Boolean esPrincipal,
            @Param("fechaRegistro") Date fechaRegistro, @Param("imagen") String imagen);
    List<Blog> findByEsPrincipal(Boolean esPrincipal);
}
