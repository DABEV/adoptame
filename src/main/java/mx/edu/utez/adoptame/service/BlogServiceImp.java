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

        return blogRepository.save(blog);
    }

    @Override
    public Blog actualizarBlog(Blog blog) {

        Blog blogActualizar = blogRepository.getById(blog.getId());

        blogActualizar.setContenido(blog.getContenido());
        blogActualizar.setTitulo(blog.getTitulo());
        blogActualizar.setEsPrincipal(blog.getEsPrincipal());
        
        return blogRepository.save(blogActualizar);
    }

    @Override
    public Blog obtenerBlog(Long id) {
        // TODO Auto-generated method stub
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
