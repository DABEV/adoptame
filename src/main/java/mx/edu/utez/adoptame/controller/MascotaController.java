package mx.edu.utez.adoptame.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

import mx.edu.utez.adoptame.dto.MascotaDto;
import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Caracter;
import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.CaracterServiceImp;
import mx.edu.utez.adoptame.service.ColorServiceImp;
import mx.edu.utez.adoptame.service.FavoritoServiceImp;
import mx.edu.utez.adoptame.service.MascotaServiceImp;
import mx.edu.utez.adoptame.service.TamanoServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;
import mx.edu.utez.adoptame.util.ImagenUtileria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    private String redirectListar = "redirect:/mascota/consultarTodas";
    private String listaTamanos = "listaTamanos";
    private String listaColores = "listaColores";
    private String listaCaracteres = "listaCaracteres";
    private String listaMascotas = "listaMascotas";
    private String msgS = "msg_success";
    private String msgE = "msg_error";
    private String favoritos = "mascota/favoritos";
    private String formRegistro = "mascota/formularioRegistro";

    @Autowired
    private MascotaServiceImp mascotaServiceImp;

    @Autowired
    private TamanoServiceImp tamanoServiceImp;

    @Autowired
    private CaracterServiceImp caracterServiceImp;

    @Autowired
    private ColorServiceImp colorServiceImp;

    @Autowired
    private FavoritoServiceImp favoritoServiceImp;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(MascotaController.class);

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
            logger.error("Error al intentar listar a todas las mascotas");
        }
        return "mascota/lista";

    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO')")
    public String editar(@RequestParam("idMascota") long id, @RequestParam("tipoMascota") boolean tipoMascota,
            Model model, RedirectAttributes attributes) {
        try {
            Mascota mascota = mascotaServiceImp.obtenerMascota(id);
            if (mascota != null) {
                model.addAttribute("mascota", mascota);

                List<Color> colores = colorServiceImp.listarColores();
                List<Caracter> listaCaracter = caracterServiceImp.listarCaracteres();
                List<Tamano> listaTamano = tamanoServiceImp.listarTamanos();

                model.addAttribute(listaCaracteres, listaCaracter);
                model.addAttribute(listaTamanos, listaTamano);
                model.addAttribute(listaColores, colores);
                return formRegistro;
            }
            attributes.addFlashAttribute(msgE, "Registro no encontrado");
        } catch (Exception e) {
            logger.error("Error al intentar acceder a actualzar una mascota");
        }
        return redirectListar + "/" + tipoMascota;
    }

    @GetMapping("/registrar")
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
    public String registrar(@ModelAttribute("mascota") MascotaDto mascota, Model model) {
        try {
            List<Color> colores = colorServiceImp.listarColores();
            List<Caracter> listaCaracter = caracterServiceImp.listarCaracteres();
            List<Tamano> listaTamano = tamanoServiceImp.listarTamanos();

            model.addAttribute(listaCaracteres, listaCaracter);
            model.addAttribute(listaTamanos, listaTamano);
            model.addAttribute(listaColores, colores);
        } catch (Exception e) {
            logger.error("Error al intentar acceder al registro de una mascota");
        }
        return formRegistro;
    }

    @PostMapping("/guardarMascota")
    @PreAuthorize("hasAuthority('ROL_VOLUNTARIO')")
    public String guardarMacota(@Valid @ModelAttribute("mascota") MascotaDto mascotaDto, BindingResult result,
            Model model, RedirectAttributes attributes, @RequestParam("imagenMascota") MultipartFile multipartFile,
            HttpSession session) {
        Mascota mascotaExistente = null;
        Mascota respuesta = null;
        String contentType = multipartFile.getContentType();

        try {
            List<Color> colores = colorServiceImp.listarColores();
            List<Caracter> listaCaracter = caracterServiceImp.listarCaracteres();
            List<Tamano> listaTamano = tamanoServiceImp.listarTamanos();

            model.addAttribute(listaCaracteres, listaCaracter);
            model.addAttribute(listaTamanos, listaTamano);
            model.addAttribute(listaColores, colores);

            if (result.hasErrors()) {
                return formRegistro;
            }
            
            Mascota mascota = modelMapper.map(mascotaDto, Mascota.class);
            boolean tipoMascota = mascota.getTipo();

            if (!multipartFile.isEmpty()) {

                if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                    attributes.addFlashAttribute(msgE, "Registro fallido solo se admiten imagenes");
                    return formRegistro;

                }
                String ruta = "C:/mascotas/img-mascotas/";
                String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
                if (nombreImagen != null) {
                    mascota.setImagen(nombreImagen);
                }
            }

            // mascota nueva
            if (mascota.getId() == null) {
                mascota.setAprobadoRegistro("pendiente");
                mascota.setDisponibleAdopcion(true);
                mascota.setActivo(true);

                respuesta = mascotaServiceImp.guardarMascota(mascota);
                UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute("usuario");
                Long idUsuario = usuarioDto.getId();
                mascotaServiceImp.procedimientoRegistrarMascota(idUsuario, respuesta);
            } else {
                // mascota existente
                mascotaExistente = mascotaServiceImp.obtenerMascota(mascota.getId());
                mascota.setFechaRegistro(mascotaExistente.getFechaRegistro());
                mascota.setDisponibleAdopcion(mascotaExistente.getDisponibleAdopcion());
                mascota.setAprobadoRegistro(mascotaExistente.getAprobadoRegistro());
                mascota.setActivo(mascotaExistente.getActivo());

                mascotaServiceImp.procedimientoActualizarMascota(mascota, mascotaExistente, session);
            }

            respuesta = mascotaServiceImp.guardarMascota(mascota);

            if (respuesta != null) {
                attributes.addFlashAttribute(msgS, "Registro exitoso");
                return redirectListar + "/" + tipoMascota;
            } else {
                attributes.addFlashAttribute(msgE, "Registro fallido");
                return "redirect:/mascota/registrar";
            }

        } catch (Exception e) {
            logger.error("Error al intentar guardar una mascota");
        }

        attributes.addFlashAttribute(msgE, "Registro fallido");
        return redirectListar;
    }

    @GetMapping("/borrarMascota/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR') or hasAuthority('ROL_VOLUNTARIO')")
    public String borrarMascota(@PathVariable long id, RedirectAttributes attributes, HttpSession session) {
        try {
            Mascota mascota = mascotaServiceImp.obtenerMascota(id);
            if (mascota != null) {
                mascotaServiceImp.eliminarMascota(id, session);
                attributes.addFlashAttribute(msgS, "Borrado con ??xito");
                return redirectListar + "/" + mascota.getTipo();
            }
        } catch (Exception e) {
            logger.error("Error al intentar borrar una mascota");
        }
        attributes.addFlashAttribute(msgE, "Ocurri?? un error");
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

            List<Mascota> listaFiltrada = mascotaServiceImp.filtrarPorParametros(color, sexo, tamano, tipoMascota);

            model.addAttribute("tipo", tipoMascota);
            model.addAttribute(listaMascotas, listaFiltrada);
            model.addAttribute(listaTamanos, tamanoServiceImp.listarTamanos());
            model.addAttribute(listaColores, colorServiceImp.listarColores());
            return "mascota/lista";

        } catch (Exception e) {
            logger.error("Error al intentar filtrar una mascota");
        }
        return redirectListar + "/" + tipoMascota;
    }

    @GetMapping("/solicitudesRegistro")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String solicitudesRegistro(Model model) {
        try {
            List<Mascota> lista = mascotaServiceImp.obtenerPendientes();
            model.addAttribute("listaPendientes", lista);
        } catch (Exception e) {
            logger.error("Error al intentar solicitar el registro de una mascota");
        }
        return "mascota/solicitudesRegistro";
    }

    @PostMapping("/validar")
    @PreAuthorize("hasAuthority('ROL_ADMINISTRADOR')")
    public String verificarRegistro(@RequestParam("idMascota") long id,
            @RequestParam("verificado") String verificado, RedirectAttributes attributes) {
        try {
            Mascota respuesta = mascotaServiceImp.validarRegistro(id, verificado);
            if (respuesta != null) {
                attributes.addFlashAttribute(msgS, "Registro verificado con ??xito");
                return "redirect:/mascota/solicitudesRegistro";
            }
        } catch (Exception e) {
            logger.error("Error al intentar verificar a una mascota");
        }
        return redirectListar;
    }

    @GetMapping("/favoritos")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String listarFavoritos(Authentication authentication, Model model) {
        try {
            String correo = authentication.getName();
            Usuario usuario = usuarioServiceImp.buscarPorCorreo(correo);

            List<Favorito> favoritosLista = favoritoServiceImp.listarFavoritos(usuario.getId());
            List<Mascota> mascotas = new ArrayList<>();
            if (!favoritos.isEmpty()) {
                for (Favorito f : favoritosLista) {
                    mascotas.add(f.getMascota());
                }
            }

            model.addAttribute(listaMascotas, mascotas);
        } catch (Exception e) {
            logger.error("Error al intentar listar favoritos");
        }
        return favoritos;
    }

    @PostMapping("/guardarFavorito")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String guardarFavorito(@RequestParam("idMascota") long idMascota,
            @RequestParam("idUsuario") long idUsuario,
            Model model, RedirectAttributes attributes) {
        try {
            Favorito favorito = favoritoServiceImp.obtenerPorMascota(idMascota, idUsuario);

            if (favorito != null) {
                attributes.addFlashAttribute(msgS, "La mascota ya se encuentra en favoritos");
                return redirectListar + "/" + favorito.getMascota().getTipo();
            } else {
                Favorito fav = new Favorito();
                fav.setMascota(mascotaServiceImp.obtenerMascota(idMascota));
                fav.setUsuario(usuarioServiceImp.obtenerUsuario(idUsuario));
                Favorito respuesta = favoritoServiceImp.guardarFavorito(fav);
                attributes.addFlashAttribute(msgS, "Favorito agregado con ??xito");
                return redirectListar + "/" + respuesta.getMascota().getTipo();
            }

        } catch (Exception e) {
            logger.error("Error al intentar guardar favoritos");
        }
        attributes.addFlashAttribute(msgE, "No se pudo agregar");
        return redirectListar;
    }

    @PostMapping("/eliminarFavorito")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String eliminarFavorito(@RequestParam("idMascota") long idMascota,
            @RequestParam("idUsuario") long idUsuario,
            Model model, RedirectAttributes attributes) {
        try {
            Favorito favorito = favoritoServiceImp.obtenerPorMascota(idMascota, idUsuario);
            if (favorito != null) {
                boolean respuesta = favoritoServiceImp.eliminarFavorito(favorito.getId());
                if (respuesta) {
                    attributes.addFlashAttribute(msgS, "Eliminado con ??xito de favoritos");
                }

                // lista de mascotas por usuario
                List<Favorito> favoritosLista = favoritoServiceImp.listarFavoritos(idUsuario);
                List<Mascota> mascotas = new ArrayList<>();
                if (!favoritos.isEmpty()) {
                    for (Favorito f : favoritosLista) {
                        mascotas.add(f.getMascota());
                    }
                }
                model.addAttribute("listaMascotas", mascotas);
                return favoritos;
            }
        } catch (Exception e) {
            logger.error("Error al intentar eliminar un favorito");
        }
        attributes.addFlashAttribute(msgE, "No se pudo elimar");
        return redirectListar;
    }
}
