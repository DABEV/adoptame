package mx.edu.utez.adoptame.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.lowagie.text.DocumentException;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.edu.utez.adoptame.config.PaypalPaymentIntent;
import mx.edu.utez.adoptame.config.PaypalPaymentMethod;
import mx.edu.utez.adoptame.dto.DonacionDto;
import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.service.DonacionServiceImp;
import mx.edu.utez.adoptame.service.PaypalServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;
import mx.edu.utez.adoptame.util.DonacionPdfExporter;
import mx.edu.utez.adoptame.util.DonacionesPdfExporter;

@Controller
@RequestMapping("/donativo")
public class DonativoController {

    private Logger logger = LoggerFactory.getLogger(DonativoController.class);

    private static final String URL_CONSULTAR_TODOS = "/donativo/consultarTodos";
    private static final String URL_INDEX = "/";
    private static final String URL_ERROR = "/error";

    private static final String REDIRECT_CT = "redirect:" + URL_CONSULTAR_TODOS;
    private static final String REDIRECT_INDEX = "redirect:" + URL_INDEX;

    static final String MSG_ERROR = "msg_error";
    static final String MSG_SUCESS = "msg_sucess";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaypalServiceImp paypalServiceImp;

    @Autowired
    private DonacionServiceImp donacionServiceImp;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @PostMapping("/guardarDonativo")
    @Transactional
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String guardarDonativo(@Valid @ModelAttribute("donacion") DonacionDto donacionDto, BindingResult result, Model model,
            RedirectAttributes redirectAttributes, HttpSession session, Authentication authentication) {
        try {
            if (result.hasErrors()) {
                return REDIRECT_INDEX;
            }

            String correo = authentication.getName();

            UsuarioDto donador = modelMapper.map(usuarioServiceImp.buscarPorCorreo(correo), UsuarioDto.class);
            donacionDto.setDonador(donador);

            boolean estado = false;

            String currency = "MXN";
            String description = "Donación ($" + donacionDto.getMonto().toString() + ") de "
                    + donacionDto.getDonador().getCorreo();

            // Guarda el pago en PayPal
            Payment pago = paypalServiceImp.creaPago(
                    donacionDto.getMonto(),
                    currency,
                    PaypalPaymentMethod.PAYPAL,
                    PaypalPaymentIntent.SALE,
                    description,
                    URL_INDEX,
                    URL_CONSULTAR_TODOS);

            for (Links links : pago.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    estado = true;
                    donacionDto.setEstado(estado);
                }
            }

            if (estado) {
                donacionDto.setAutorizacion(pago.getId());

                Donacion donacion = modelMapper.map(donacionDto, Donacion.class);

                boolean respuesta = donacionServiceImp.guardarDonacion(donacion, session);

                if (respuesta) {
                    redirectAttributes.addFlashAttribute(MSG_SUCESS, "Registro exitoso");
                    return REDIRECT_CT;
                } else {
                    redirectAttributes.addFlashAttribute(MSG_ERROR, "Registro fallido");
                    return REDIRECT_INDEX;
                }
            } else {
                redirectAttributes.addFlashAttribute(MSG_ERROR, "Registro fallido: No se genero el pago de PayPal");
                return REDIRECT_INDEX;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute(MSG_ERROR, "Registro fallido: Error en el servidor");
            return REDIRECT_INDEX;
        }
    }

    @GetMapping("/webhook")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalServiceImp.ejecutaPago(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                return REDIRECT_CT;
            }

            return REDIRECT_INDEX;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return REDIRECT_INDEX;
        }
    }

    @GetMapping("/consultarTodos")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String consultarTodos(Authentication authentication, Model model,
            RedirectAttributes redirectAttributes, Pageable pageable) {
        try {
            String correo = authentication.getName();
            Usuario donador = usuarioServiceImp.buscarPorCorreo(correo);

            Page<Donacion> listaDonaciones = donacionServiceImp
                .listarDonacionesDelUsuario(donador, PageRequest.of(pageable.getPageNumber(), 5, Sort.by("fechaDonacion").descending()));
            
            model.addAttribute("listaDonaciones", listaDonaciones);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "donacion/listaDonaciones";
    }

    @GetMapping("/donar")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String crearDonacion(@ModelAttribute("donacion") DonacionDto donacionDto, Model modelo) {
        return "usuario/formDonacion";
    }

    @GetMapping("/consultaUnica/{id}")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public String consultaUnica(@PathVariable Long id, Model modelo, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String correo = authentication.getName();
            Usuario donador = usuarioServiceImp.buscarPorCorreo(correo);
            Donacion donacion = donacionServiceImp.obtenerDonacion(id);

            if (donacion != null) {
                // Verifica que la donación pertenezca al usuario
                if (donacion.getDonador().getId().equals(donador.getId())) {
                    modelo.addAttribute("donacion", donacion);
                    return "usuario/listarDonacion";
                } else {
                    redirectAttributes.addFlashAttribute(MSG_ERROR, "Oops, ha ocurrido un error.");        
                    return REDIRECT_INDEX;
                }
            }
            redirectAttributes.addFlashAttribute(MSG_ERROR, "Donacion no econtrada");
            return REDIRECT_CT;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return REDIRECT_CT;
        }
    }

    @GetMapping("/donaciones/export/{id}")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public void exportManyToPDF(@PathVariable Long id, HttpServletResponse response, Authentication authentication) throws DocumentException, IOException {
        try {
            String correo = authentication.getName();
            Usuario donador = usuarioServiceImp.buscarPorCorreo(correo);
            
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=recibo_donacion_" + currentDateTime + ".pdf";
        
            response.setHeader(headerKey, headerValue);

            Donacion donacion = donacionServiceImp.obtenerDonacion(id);
        
            if (donacion.getDonador().getId().equals(donador.getId())) {
                DonacionPdfExporter exporter = new DonacionPdfExporter(donacion);
                exporter.export(response);
            } else {
                response.sendRedirect(URL_ERROR);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.sendRedirect(URL_ERROR);
        }
    }

    @GetMapping("/donaciones/exportMany")
    @PreAuthorize("hasAuthority('ROL_ADOPTADOR')")
    public void exportToPDF(HttpServletResponse response, Authentication authentication) throws DocumentException, IOException {
        try {
            String correo = authentication.getName();
            Usuario donador = usuarioServiceImp.buscarPorCorreo(correo);

            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=donaciones_" + currentDateTime + ".pdf";

            response.setHeader(headerKey, headerValue);

            List<Donacion> listDonaciones = donacionServiceImp.listarDonacionesDelUsuario(donador);

            DonacionesPdfExporter exporter = new DonacionesPdfExporter(listDonaciones);
            exporter.export(response);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.sendRedirect(URL_ERROR);
        }
    }
}
