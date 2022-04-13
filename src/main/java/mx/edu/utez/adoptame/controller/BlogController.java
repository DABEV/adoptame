package mx.edu.utez.adoptame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    BlogServiceImp blogServiceImp;

    private String redirectBlogLista = "redirect:/blog/consultarTodas";

    @GetMapping("/consultarTodas")
    public String consultarBlogs(Blog blog, Model model) {

        try {
            model.addAttribute("listaBlog", blogServiceImp.listarBlogs());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "blog/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable("id") long id) {
        blogServiceImp.obtenerBlog(id);
        return "";
    }

    @PostMapping("/guardarBlog")
    public String guardarBlog(Blog blog, Model model, RedirectAttributes redirectAttributes,
            @RequestParam("imagenBlog") MultipartFile multipartFile) {

        try {
            if (!multipartFile.isEmpty()) {
                String ruta = "C:/mascotas/img-mascotas/";
                String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
                if (nombreImagen != null) {
                    blog.setImagen(nombreImagen);
                }
            }

            Blog respuesta = blogServiceImp.guardarBlog(blog);
            if (respuesta != null) {
                redirectAttributes.addFlashAttribute("msg_success", "Registro exitoso");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectBlogLista;

    }

    @PostMapping("/actualizarBlog")
    public String actualizarBlog(Blog blog, Model model, RedirectAttributes redirectAttributes,
            @RequestParam("imagenBlog") MultipartFile multipartFile) {

        try {

            if (!multipartFile.isEmpty()) {
                String ruta = "C:/mascotas/img-mascotas/";
                String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
                if (nombreImagen != null) {
                    blog.setImagen(nombreImagen);
                }
            }

            Blog respuesta = blogServiceImp.actualizarBlog(blog);
            if (respuesta != null) {
                redirectAttributes.addFlashAttribute("msg_success", "Registro exitoso");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectBlogLista;

    }

    @PostMapping("/borrarBlog")
    public String borrarBlog(@RequestParam("idBlog") long id, RedirectAttributes redirectAttributes) {
        try {
            Boolean respuestaEliminacion = blogServiceImp.eliminarBlog(id);
            if (Boolean.TRUE.equals(respuestaEliminacion)) {
                redirectAttributes.addFlashAttribute("msg_success", "Registro exitoso");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectBlogLista;
    }

}
