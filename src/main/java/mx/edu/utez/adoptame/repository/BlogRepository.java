package mx.edu.utez.adoptame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
        @Query(value = "{call registroBlog(:usuario_id, :#{#blog.titulo}, :#{#blog.contenido}, :#{#blog.esPrincipal}, :#{#blog.fechaRegistro}, :#{#blog.imagen})}", nativeQuery = true)
        List<Blog> registroBlog(@Param("usuario_id") long usuarioId, @Param("blog") Blog blog);

        @Query(value = "{call actualizarBlog(:usuario_id, :#{#anterior.titulo}, :#{#anterior.contenido}, :#{#anterior.esPrincipal}, :#{#anterior.fechaRegistro}, :#{#anterior.imagen}, :#{#blog.titulo}, :#{#blog.contenido}, :#{#blog.esPrincipal}, :#{#blog.fechaRegistro}, :#{#blog.imagen})}", nativeQuery = true)
        List<Blog> actualizarBlog(@Param("usuario_id") long usuarioId, @Param("blog") Blog blog,
                        @Param("anterior") Blog anterior);

        @Query(value = "{call eliminarBlog(:usuario_id,:#{#blog.titulo}, :#{#blog.contenido}, :#{#blog.esPrincipal}, :#{#blog.fechaRegistro}, :#{#blog.imagen})}", nativeQuery = true)
        List<Blog> eliminarBlog(@Param("usuario_id") long usuarioId, @Param("blog") Blog blog);

        List<Blog> findByEsPrincipal(Boolean esPrincipal);
}
