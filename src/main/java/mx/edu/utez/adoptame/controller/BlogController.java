package mx.edu.utez.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

    
    @GetMapping("/consultarTodas")
    public String consultarBlogs(){
        return "";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(){
        return "";
    }

    @PostMapping("/guardarBlog")
    public String guardarBlog(){
        return "";
    }

    @PostMapping("/borrarBlog")
    public String borrarBlog(){
        return "";
    }
    
}
