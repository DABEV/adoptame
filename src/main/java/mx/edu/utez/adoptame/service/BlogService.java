package mx.edu.utez.adoptame.service;

import java.util.List;

public interface BlogService {
    List<Blog> listarBlogs();
    Blog guardarBlog(Blog blog);
    Blog actualizarBlog (Blog blog);
    Blog obtenerBlog(long id);
    boolean eliminarBlog(long id);
}
