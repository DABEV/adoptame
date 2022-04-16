package mx.edu.utez.adoptame.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.service.BlogServiceImp;
import mx.edu.utez.adoptame.util.ImagenUtileria;

@Controller
@RequestMapping("/blog")
public class BlogController {

    Log log = LogFactory.getLog(getClass());

    private String msgSuccess = "msg_success";
    private String msgError = "msg_error";

    @Autowired
    BlogServiceImp blogServiceImp;

    private String redirectBlogLista = "redirect:/blog/consultarTodas";

    @GetMapping("/consultarTodas")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String consultarBlogs(Blog blog, Model model, HttpSession session) {

        try {
            model.addAttribute("listaBlog", blogServiceImp.listarBlogs());

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return "blog/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable("id") long id) {
        blogServiceImp.obtenerBlog(id);
        return "";
    }

    @PostMapping("/guardarBlog")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String guardarBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult result, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("imagenBlog") MultipartFile multipartFile, HttpSession session) {

        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute(msgError, "Registro fallido");

                return redirectBlogLista;

            } else if (!multipartFile.isEmpty()) {
                String ruta = "C:/mascotas/img-mascotas/";
                String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
                if (nombreImagen != null) {
                    blog.setImagen(nombreImagen);
                }
            }

            Blog respuesta = blogServiceImp.guardarBlog(blog, session);
            if (respuesta != null) {
                redirectAttributes.addFlashAttribute(msgSuccess, "Registro exitoso");
            } else {
                redirectAttributes.addFlashAttribute(msgError, "Registro fallido verifique los datos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return redirectBlogLista;

    }

    @PostMapping("/actualizarBlog")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String actualizarBlog(Blog blog, Model model, RedirectAttributes redirectAttributes,
            @RequestParam("imagenBlog") MultipartFile multipartFile, HttpSession session) {

        try {

            if (!multipartFile.isEmpty()) {
                String ruta = "C:/mascotas/img-mascotas/";
                String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
                if (nombreImagen != null) {
                    blog.setImagen(nombreImagen);
                }
            }

            Blog respuesta = blogServiceImp.actualizarBlog(blog, session);
            if (respuesta != null) {
                redirectAttributes.addFlashAttribute(msgSuccess, "Actualizacion exitosa");
            } else {
                redirectAttributes.addFlashAttribute(msgError, "Actualizacion fallida verifique los datos");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return redirectBlogLista;

    }

    @PostMapping("/borrarBlog")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String borrarBlog(@RequestParam("idBlog") long id, RedirectAttributes redirectAttributes,
            HttpSession session) {
        try {
            Boolean respuestaEliminacion = blogServiceImp.eliminarBlog(id, session);
            if (Boolean.TRUE.equals(respuestaEliminacion)) {
                redirectAttributes.addFlashAttribute(msgSuccess, "eliminacion exitosa");
            } else {
                redirectAttributes.addFlashAttribute(msgError, "eliminacion fallida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }
        return redirectBlogLista;
    }

}
