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

import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.service.BlogServiceImp;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogServiceImp blogServiceImp;

    private String blogLista = "redirect:/blog/consultarTodas";

    @GetMapping("/consultarTodas")
    public String consultarBlogs(Blog blog, Model model) {
        model.addAttribute("listaBlog", blogServiceImp.listarBlogs());
        return "blog/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable("id") long id) {
        blogServiceImp.obtenerBlog(id);
        return "";
    }

    @PostMapping("/guardarBlog")
    public String guardarBlog(Blog blog, Model model, @RequestParam("imagenMascota") MultipartFile multipartFile) {

        blog.setImagen("imagen");

        blogServiceImp.guardarBlog(blog);

        return blogLista;
    }

    @PostMapping("/actualizarBlog")
    public String actualizarBlog(Blog blog) {
        blog.setImagen("imagen");

        blogServiceImp.actualizarBlog(blog);
        return blogLista;
    }

    @PostMapping("/borrarBlog")
    public String borrarBlog(@RequestParam("idBlog") long id) {
        blogServiceImp.eliminarBlog(id);
        return blogLista;
    }

}
