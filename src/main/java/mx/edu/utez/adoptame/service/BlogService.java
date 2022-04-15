package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogService {
    List<Blog> listarBlogs();

    Blog guardarBlog(Blog blog, HttpSession session);

    Blog actualizarBlog(Blog blog);

    Blog obtenerBlog(Long id);

    boolean eliminarBlog(Long id);

    List<Blog> procedimientoRegistrarBlog(Long idUsuario, String titulo, String contenido,
            Boolean esPrincipal, Date fechaRegistro, String imagen);

    List<Blog> procedimientoActualizarBlog(Long idUsuario, String tituloAnterior, String contenidoAnterior,
            Boolean esPrincipalAnterior, Date fechaRegistroAnterior, String imagenAnterior, String titulo,
            String contenido,
            Boolean esPrincipal, Date fechaRegistro, String imagen);

    List<Blog> procedimientoEliminarBlog(Long idUsuario, String titulo, String contenido,
    Boolean esPrincipal, Date fechaRegistro, String imagen);
}
