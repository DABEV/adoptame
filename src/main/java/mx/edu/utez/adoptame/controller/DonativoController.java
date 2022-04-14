package mx.edu.utez.adoptame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.service.DonacionServiceImp;

@Controller
@RequestMapping("/donativo")
public class DonativoController {

    @Autowired
    private DonacionServiceImp donacionServiceImp;
    

    @GetMapping("/consultarTodos")
    public String consultarTodos(Model model,
            RedirectAttributes redirectAttributes, Pageable pageable) {
        Page<Donacion> listaDonaciones = donacionServiceImp
                .listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("fechaDonacion").descending()));
        model.addAttribute("listaDonaciones", listaDonaciones);
        return "donacion/listaDonaciones";
    }

    @GetMapping("/donar")
    public String crearDonacion(Donacion donacion, Model modelo) {
        return "usuario/formDonacion";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Donacion donacion = donacionServiceImp.obtenerDonacion(id);
        if (!donacion.equals(null)) {
            modelo.addAttribute("donacion", donacion);
            return "usuario/listarDonacion";

        }
        redirectAttributes.addFlashAttribute("msg_error", "Donacion no econtrada");
        return "redirect:/donativo/consultarTodos";
    }

    @PostMapping("/guardarDonativo")
    public String guardarDonativo(Donacion donacion, Model model, RedirectAttributes redirectAttributes) {
        donacion.setEstado(false);
        boolean respuesta = donacionServiceImp.guardarDonacion(donacion);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "Registro exitoso");
            return "redirect:/donativo/consultarTodos";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
            return "redirect:/donativo/donar";
        }

    }

    @PostMapping("/borrarDonativo/{id}")
    public String borrarDonativo(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        boolean respuesta = donacionServiceImp.eliminarDonacion(id);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Eliminacion fallida");
        }
        return "redirect:/donativo/consultarTodos";
    }

}
