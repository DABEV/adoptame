package mx.edu.utez.adoptame.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import mx.edu.utez.adoptame.model.Caracter;
import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.service.CaracterServiceImpl;
import mx.edu.utez.adoptame.service.ColorServiceImp;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.service.TamanoServiceImp;
import mx.edu.utez.adoptame.util.ImagenUtileria;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    private String redirectListar = "redirect:/mascota/consultarTodas";
    private String listaTamanos = "listaTamanos";
    private String listaColores = "listaColores";
    private String listaCaracteres = "listaCaracteres";
    private String listaMascotas = "listaMascotas";

    @Autowired
    MascotaServiceImp mascotaServiceImp;

    @Autowired
    TamanoServiceImp tamanoServiceImp;

    @Autowired
    CaracterServiceImpl caracterServiceImp;

    @Autowired
    ColorServiceImp colorServiceImp;

    @GetMapping(value = { "/consultarTodas", "/consultarTodas/{tipoMascota}" })
    public String consultarMascotas(@PathVariable(required = false) String tipoMascota, Model model) {
        try {
            if (tipoMascota != null) {
                boolean tipo = Boolean.parseBoolean(tipoMascota);
                model.addAttribute(listaMascotas, mascotaServiceImp.listarMascotas(tipo));
                model.addAttribute("tipo", tipo);
            } else {
                model.addAttribute(listaMascotas, mascotaServiceImp.listarMascotas());
            }

            model.addAttribute(listaTamanos, tamanoServiceImp.listarTamanos());
            model.addAttribute(listaColores, colorServiceImp.listarColores());

        } catch (Exception e) {
            // log
        }
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

    @PostMapping("/actualizar")
    public String editar(@RequestParam("idMascota") long id, @RequestParam("tipoMascota") boolean tipoMascota,
            Model model, RedirectAttributes attributes) {
        Mascota mascota = mascotaServiceImp.obtenerMascota(id);
        if (mascota != null) {
            model.addAttribute("mascota", mascota);

            List<Color> colores = colorServiceImp.listarColores();
            List<Caracter> listaCaracter = caracterServiceImp.listarCaracteres();
            List<Tamano> listaTamano = tamanoServiceImp.listarTamanos();

            model.addAttribute(listaCaracteres, listaCaracter);
            model.addAttribute(listaTamanos, listaTamano);
            model.addAttribute(listaColores, colores);
            return "mascota/formularioRegistro";
        }
        attributes.addFlashAttribute("msg_error", "Registro no encontrado");
        return "redirect:/mascota/consultarTodas/" + tipoMascota;
    }

    @GetMapping("/registrar")
    public String registrar(Mascota mascota, Model model) {
        List<Color> colores = colorServiceImp.listarColores();
        List<Caracter> listaCaracter = caracterServiceImp.listarCaracteres();
        List<Tamano> listaTamano = tamanoServiceImp.listarTamanos();

        model.addAttribute(listaCaracteres, listaCaracter);
        model.addAttribute(listaTamanos, listaTamano);
        model.addAttribute(listaColores, colores);
        return "mascota/formularioRegistro";
    }

    @PostMapping("/guardarMascota")
    public String guardarMacota(Mascota mascota, Model model, RedirectAttributes attributes,
            @RequestParam("imagenMascota") MultipartFile multipartFile) {
        if (mascota.getId() == null) {
            mascota.setAprobadoRegistro("pendiente");
            mascota.setDisponibleAdopcion(false);
            mascota.setActivo(true);

        } else {
            Mascota mascotaExistente = mascotaServiceImp.obtenerMascota(mascota.getId());
            mascota.setFechaRegistro(mascotaExistente.getFechaRegistro());
            mascota.setDisponibleAdopcion(mascotaExistente.getDisponibleAdopcion());
            mascota.setAprobadoRegistro(mascotaExistente.getAprobadoRegistro());
            mascota.setActivo(mascotaExistente.getActivo());
        }

        boolean tipoMascota = mascota.getTipo();

        if (!multipartFile.isEmpty()) {
            String ruta = "C:/mascotas/img-mascotas/";
            String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
            if (nombreImagen != null) {
                mascota.setImagen(nombreImagen);
            }
        }

        Mascota respuesta = mascotaServiceImp.guardarMascota(mascota);
        if (respuesta != null) {
            attributes.addFlashAttribute("msg_success", "Registro exitoso");
            return "redirect:/mascota/consultarTodas/" + tipoMascota;
        } else {
            attributes.addFlashAttribute("msg_error", "Registro fallido");
            return "redirect:/mascota/registrar";
        }

    }

    @GetMapping("/borrarMascota/{id}")
    public String borrarMascota(@PathVariable long id) {
        mascotaServiceImp.eliminarMascota(id);
        return redirectListar;
    }

    @PostMapping("/filtrar")
    public String filtrarMascotas(@RequestParam("colorMascota") String colorId,
            @RequestParam("tipoMascota") boolean tipoMascota,
            @RequestParam("sexoMascota") String sexoMascota,
            @RequestParam("tamanoMascota") String tamanoId,
            Model model) {
        try {
            Color color = new Color();
            Tamano tamano = new Tamano();
            boolean sexo = false;

            if (!colorId.isEmpty()) {
                color = colorServiceImp.obtenerColor(Long.parseLong(colorId));
            }
            if (!tamanoId.isEmpty()) {
                tamano = tamanoServiceImp.obtenerTamano(Long.parseLong(tamanoId));
            }
            if (!sexoMascota.isEmpty()) {
                sexo = Boolean.parseBoolean(sexoMascota);
            }

            List<Mascota> listaFiltrada = mascotaServiceImp.filtrarPorParametros(color, sexo, tamano);

            // Filtro para mascotas activas en el sistema
            listaFiltrada = listaFiltrada.stream().filter(Mascota::getActivo)
                    .collect(Collectors.toList());

            // filtro de tipo
            listaFiltrada = listaFiltrada.stream().filter(mascota -> mascota.getTipo() == tipoMascota)
                    .collect(Collectors.toList());

            model.addAttribute("tipo", tipoMascota);
            model.addAttribute(listaMascotas, listaFiltrada);
            model.addAttribute(listaTamanos, tamanoServiceImp.listarTamanos());
            model.addAttribute(listaColores, colorServiceImp.listarColores());
            return "mascota/lista";

        } catch (Exception e) {
            return redirectListar;
        }
    }
}
