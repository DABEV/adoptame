package mx.edu.utez.adoptame.controller;

import java.util.List;

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

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.service.ColorServiceImp;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.util.ImagenUtileria;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    private String redirectListar = "redirect:/mascota/consultarTodas";

    @Autowired
    MascotaServiceImp mascotaServiceImp;

    @Autowired
    ColorServiceImp colorServiceImp;

    @GetMapping("/consultarTodas")
    public String consultarMascotas(Model model) {
        model.addAttribute("listaMascotas", mascotaServiceImp.listarMascotas());
        return "mascota/lista";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable long id, Model model) {
        Mascota mascota = mascotaServiceImp.obtenerMascota(id);
		if (mascota != null) {
			model.addAttribute("mascota", mascota);
			return "mascota/detalles";
		}
        return redirectListar;
    }

    @GetMapping("/actualizar/{id}")
    public String editar(@PathVariable long id, Model model, RedirectAttributes attributes) {
        Mascota mascota = mascotaServiceImp.obtenerMascota(id);
        if (mascota != null) {
            model.addAttribute("mascota", mascota);

            List<Color> colores = colorServiceImp.listarColores();
            /*
             * List<Caracter> listaCaracter = caracterService.listar();
             * List<Tamano> listaTamano = tamanoService.listar();
             * 
             * model.addAttribute("listaCaracteres", listaCaracter);
             * model.addAttribute("listaTamanos", listaTamano);
             */
            model.addAttribute("listaColores", colores);
            return "mascota/formularioRegistro";
        }
        attributes.addFlashAttribute("msg_error", "Registro no encontrado");
        return redirectListar;
    }

    @GetMapping("/registrar")
    public String registrar(Mascota mascota, Model model) {
        List<Color> colores = colorServiceImp.listarColores();
        /*
         * List<Caracter> listaCaracter = caracterService.listar();
         * List<Tamano> listaTamano = tamanoService.listar();
         * 
         * model.addAttribute("listaCaracteres", listaCaracter);
         * model.addAttribute("listaTamanos", listaTamano);
         */
        model.addAttribute("listaColores", colores);
        return "mascota/formularioRegistro";
    }

    @PostMapping("/guardarMascota")
    public String guardarMacota(Mascota mascota, Model model, RedirectAttributes attributes,
            @RequestParam("imagenMascota") MultipartFile multipartFile) {
        if (mascota.getId() == null) {
            mascota.setAprobadoRegistro("pendiente");
            mascota.setDisponibleAdopcion(false);

        } else {
            Mascota mascotaExistente = mascotaServiceImp.obtenerMascota(mascota.getId());
            mascota.setFechaRegistro(mascotaExistente.getFechaRegistro());
            mascota.setDisponibleAdopcion(mascotaExistente.getDisponibleAdopcion());
            mascota.setAprobadoRegistro(mascotaExistente.getAprobadoRegistro());
        }

        if (!multipartFile.isEmpty()) {
            // Establecer directorio local para subida de archivos; en prod: /var/www/html
            String ruta = "C:/mascotas/img-mascotas/";
            String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
            if (nombreImagen != null) {
                mascota.setImagen(nombreImagen);
            }
        }

        Mascota respuesta = mascotaServiceImp.guardarMascota(mascota);
        if (respuesta != null) {
            attributes.addFlashAttribute("msg_success", "Registro exitoso");
            return redirectListar;
        } else {
            attributes.addFlashAttribute("msg_error", "Registro fallido");
            return "redirect:/mascotas/registrar";
        }

    }

    @PostMapping("/borrarMascota")
    public String borrarMascota() {
        return "";
    }

}
