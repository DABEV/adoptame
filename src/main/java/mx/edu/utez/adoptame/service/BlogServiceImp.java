package mx.edu.utez.adoptame.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.repository.BlogRepository;

@Service
public class BlogServiceImp implements BlogService {

    Log log = LogFactory.getLog(getClass());

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    private Long idUsuario;
    private String usuarioSession = "usuario";

    @Override

    public List<Blog> listarBlogs() {

        return blogRepository.findAll();
    }

    @Override
    public Blog guardarBlog(Blog blog, HttpSession session) {
        try {

            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();

            Blog respuesta = blogRepository.save(blog);
            if (respuesta.getTitulo() != null) {
                procedimientoRegistrarBlog(idUsuario, respuesta);
                return respuesta;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Blog> procedimientoRegistrarBlog(Long id, Blog blog) {

        return blogRepository.registroBlog(id, blog);

    }

    @Override
    public Blog actualizarBlog(Blog blog, HttpSession session) {
        try {
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();
            Blog blogActualizar = blogRepository.getById(blog.getId());

            blogActualizar.setContenido(blog.getContenido());
            blogActualizar.setTitulo(blog.getTitulo());
            blogActualizar.setEsPrincipal(blog.getEsPrincipal());

            if (blog.getImagen() == null) {
                blog.setImagen(blogActualizar.getImagen());

                procedimientoActualizarBlog(idUsuario, blog, blogActualizar);
                blogRepository.save(blogActualizar);

            } else {
                blogActualizar.setImagen(blog.getImagen());

                procedimientoActualizarBlog(idUsuario, blog, blogActualizar);
                blogRepository.save(blogActualizar);
            }

            return blogActualizar;
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return null;

    }

    @Override
    public List<Blog> procedimientoActualizarBlog(Long idUsuario, Blog blog, Blog anterior) {

        return blogRepository.actualizarBlog(idUsuario, blog, anterior);
    }

    @Override
    public Blog obtenerBlog(Long id) {

        try {

            Blog blog = blogRepository.getById(id);

            if (blog.getTitulo() != null) {
                return blog;
            }

        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return null;
    }

    @Override
    public boolean eliminarBlog(Long id, HttpSession session) {
        try {

            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();
            Blog blog = blogRepository.getById(id);

            if (blog.getId() != null) {
                procedimientoEliminarBlog(idUsuario, blog);
                blogRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return false;
    }

    @Override
    public List<Blog> procedimientoEliminarBlog(Long idUsuario, Blog blog) {
        return blogRepository.eliminarBlog(idUsuario, blog);

    }

    public List<Blog> listaPrincipales() {
        List<Blog> lista = null;
        try {
            lista = blogRepository.findByEsPrincipal(true);
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return lista;
    }

}
