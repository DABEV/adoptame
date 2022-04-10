package mx.edu.utez.adoptame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.edu.utez.adoptame.model.Blog;
import mx.edu.utez.adoptame.service.BlogServiceImp;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogServiceImp blogServiceImp;

    
    @GetMapping("/consultarTodas")
    public String consultarBlogs(Model model){
        model.addAttribute("listaBlog", blogServiceImp.listarBlogs());
        return "blog/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable("id") long id){
        blogServiceImp.obtenerBlog(id);
        return "";
    }

    @PostMapping("/guardarBlog")
    public String guardarBlog(Blog blog){

        blogServiceImp.guardarBlog(blog);

        return "blog/lista";
    }

    @GetMapping("/actualizarBlog")
    public String actualizarBlog(Blog blog){
        blogServiceImp.actualizarBlog(blog);
        return "blog/lista";
    }

    @PostMapping("/borrarBlog/{id}")
    public String borrarBlog(@PathVariable("id") long id){
        blogServiceImp.eliminarBlog(id);
        return "blog/lista";
    }
    
}
