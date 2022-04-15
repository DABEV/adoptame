package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.repository.BlogRepository;

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UsuarioServiceImp usuarioServiceImp;




    @Override
    public List<Blog> listarBlogs() {

        return blogRepository.findAll();
    }


    @Override
    public Blog guardarBlog(Blog blog, HttpSession session) {
        try {

            Long id = (Long) session.getAttribute("id");
            System.out.println(id);

            Blog respuesta = blogRepository.save(blog);
            if (respuesta.getTitulo() != null) {
                procedimientoRegistrarBlog(id, respuesta.getTitulo(), respuesta.getContenido(),
                        respuesta.getEsPrincipal(), respuesta.getFechaRegistro(), respuesta.getImagen());
                return respuesta;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Blog> procedimientoRegistrarBlog(Long id, String titulo, String contenido,
            Boolean esPrincipal, Date fechaRegistro, String imagen) {

        return blogRepository.registroBlog(id, titulo, contenido, esPrincipal, fechaRegistro, imagen);

    }

    @Override
    public Blog actualizarBlog(Blog blog) {

        try {
            Blog blogActualizar = blogRepository.getById(blog.getId());

            if (blog.getImagen() == null) {
                procedimientoActualizarBlog((long) 2, blogActualizar.getTitulo(),
                        blogActualizar.getContenido(), blogActualizar.getEsPrincipal(),
                        blogActualizar.getFechaRegistro(),
                        blogActualizar.getImagen(), blog.getTitulo(), blog.getContenido(),
                        blog.getEsPrincipal(), blogActualizar.getFechaRegistro(), blogActualizar.getImagen());
                blogActualizar.setImagen(blogActualizar.getImagen());
                blogActualizar.setContenido(blog.getContenido());
                blogActualizar.setTitulo(blog.getTitulo());
                blogActualizar.setEsPrincipal(blog.getEsPrincipal());
                blogRepository.save(blogActualizar);

            } else {
                procedimientoActualizarBlog((long) 2, blogActualizar.getTitulo(),
                        blogActualizar.getContenido(), blogActualizar.getEsPrincipal(),
                        blogActualizar.getFechaRegistro(),
                        blogActualizar.getImagen(), blog.getTitulo(), blog.getContenido(),
                        blog.getEsPrincipal(), blogActualizar.getFechaRegistro(), blog.getImagen());
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
    public List<Blog> procedimientoActualizarBlog(Long idUsuario, String tituloAnterior, String contenidoAnterior,
            Boolean esPrincipalAnterior, Date fechaRegistroAnterior, String imagenAnterior, String titulo,
            String contenido, Boolean esPrincipal, Date fechaRegistro, String imagen) {

        return blogRepository.actualizarBlog(idUsuario, tituloAnterior, contenidoAnterior, esPrincipalAnterior,
                fechaRegistroAnterior, imagenAnterior, titulo, contenido, esPrincipal, fechaRegistro, imagen);
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
            procedimientoEliminarBlog((long) 2, blog.getTitulo(), blog.getContenido(), blog.getEsPrincipal(),
                    blog.getFechaRegistro(), blog.getImagen());
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Blog> procedimientoEliminarBlog(Long idUsuario, String titulo, String contenido, Boolean esPrincipal,
            Date fechaRegistro, String imagen) {
        return blogRepository.eliminarBlog(idUsuario, titulo, contenido, esPrincipal, fechaRegistro, imagen);

    }

}
