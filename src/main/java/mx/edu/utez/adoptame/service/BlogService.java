package mx.edu.utez.adoptame.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogService {
        List<Blog> listarBlogs();
        Blog guardarBlog(Blog blog, HttpSession session);
        Blog actualizarBlog(Blog blog, HttpSession session);
        Blog obtenerBlog(Long id);
        boolean eliminarBlog(Long id, HttpSession session);
        List<Blog> procedimientoRegistrarBlog(Long idUsuario,Blog blog);
        List<Blog> procedimientoActualizarBlog(Long idUsuario, Blog blog, Blog anterior);
        List<Blog> procedimientoEliminarBlog(Long idUsuario, Blog blog);
        List<Blog> listaPrincipales();
}
