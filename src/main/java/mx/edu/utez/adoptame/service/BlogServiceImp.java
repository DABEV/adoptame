package mx.edu.utez.adoptame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.repository.BlogRepository;

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Blog> listarBlogs() {

        return blogRepository.findAll();
    }

    @Override
    public Blog guardarBlog(Blog blog) {

        try {

            Blog respuesta = blogRepository.save(blog);
            if (respuesta.getTitulo() != null) {
                return respuesta;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Blog actualizarBlog(Blog blog) {

        try {
            Blog blogActualizar = blogRepository.getById(blog.getId());

            if (blog.getImagen() == null) {
                blogActualizar.setImagen(blogActualizar.getImagen());
                blogActualizar.setContenido(blog.getContenido());
                blogActualizar.setTitulo(blog.getTitulo());
                blogActualizar.setEsPrincipal(blog.getEsPrincipal());
                blogRepository.save(blogActualizar);
            } else {
                blogActualizar.setImagen(blog.getImagen());
                blogActualizar.setContenido(blog.getContenido());
                blogActualizar.setTitulo(blog.getTitulo());
                blogActualizar.setEsPrincipal(blog.getEsPrincipal());
                blogRepository.save(blogActualizar);
            }

            return blogActualizar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Blog obtenerBlog(Long id) {

        try {

            Blog blog = blogRepository.getById(id);

            if (blog.getTitulo() != null) {
                return blog;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean eliminarBlog(Long id) {
        Blog blog = blogRepository.getById(id);

        if (blog.getId() != null) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
