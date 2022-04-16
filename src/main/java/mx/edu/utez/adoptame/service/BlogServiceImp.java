package mx.edu.utez.adoptame.service;

import java.util.Date;
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
                procedimientoRegistrarBlog(idUsuario, respuesta.getTitulo(), respuesta.getContenido(),
                        respuesta.getEsPrincipal(), respuesta.getFechaRegistro(), respuesta.getImagen());
                return respuesta;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return null;
    }

    @Override
    public List<Blog> procedimientoRegistrarBlog(Long id, String titulo, String contenido,
            Boolean esPrincipal, Date fechaRegistro, String imagen) {

        return blogRepository.registroBlog(id, titulo, contenido, esPrincipal, fechaRegistro, imagen);

    }

    @Override
    public Blog actualizarBlog(Blog blog, HttpSession session) {

        try {

            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute(usuarioSession);
            idUsuario = usuarioDto.getId();
            Blog blogActualizar = blogRepository.getById(blog.getId());

            if (blog.getImagen() == null) {
                procedimientoActualizarBlog(idUsuario, blogActualizar.getTitulo(),
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
                procedimientoActualizarBlog(idUsuario, blogActualizar.getTitulo(),
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
            log.error(e.getMessage());

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
                procedimientoEliminarBlog(idUsuario, blog.getTitulo(), blog.getContenido(), blog.getEsPrincipal(),
                        blog.getFechaRegistro(), blog.getImagen());
                blogRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return false;
    }

    @Override
    public List<Blog> procedimientoEliminarBlog(Long idUsuario, String titulo, String contenido, Boolean esPrincipal,
            Date fechaRegistro, String imagen) {
        return blogRepository.eliminarBlog(idUsuario, titulo, contenido, esPrincipal, fechaRegistro, imagen);

    }

    public List<Blog> listaPrincipales() {
        List<Blog> lista = null;
        try {
            lista = blogRepository.findByEsPrincipal(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return lista;
    }

}
