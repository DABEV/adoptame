package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogService {
    List<Blog> listarBlogs();
    Blog guardarBlog(Blog blog);
    Blog actualizarBlog (Blog blog);
    Blog obtenerBlog(Long id);
    boolean eliminarBlog(Long id);
}
