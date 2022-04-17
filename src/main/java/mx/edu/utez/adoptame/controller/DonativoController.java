package mx.edu.utez.adoptame.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import org.springframework.security.core.Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import mx.edu.utez.adoptame.config.PaypalPaymentIntent;
import mx.edu.utez.adoptame.config.PaypalPaymentMethod;
import mx.edu.utez.adoptame.dto.DonacionDto;
import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.service.DonacionServiceImp;
import mx.edu.utez.adoptame.service.PaypalServiceImp;
import mx.edu.utez.adoptame.service.UsuarioServiceImp;

@Controller
@RequestMapping("/donativo")
public class DonativoController {

    private static final String URL_CONSULTAR_TODOS = "/donativo/consultarTodos";
    private static final String URL_INDEX = "/";
    
    private static final String REDIRECT_CT = "redirect:" + URL_CONSULTAR_TODOS;
    private static final String REDIRECT_INDEX = "redirect:" + URL_INDEX;

    private static final String MSG_ERROR = "msg_error";
    private static final String MSG_SUCESS = "msg_sucess";

    Log log = LogFactory.getLog(getClass());

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
    public String guardarDonativo(@ModelAttribute("donacion") DonacionDto donacionDto, Model model, RedirectAttributes redirectAttributes, HttpSession session, Authentication authentication) {
        try {
            String correo = authentication.getName();

            UsuarioDto donador = modelMapper.map(usuarioServiceImp.buscarPorCorreo(correo), UsuarioDto.class); 
            donacionDto.setDonador(donador);

            boolean estado = false;

            String currency = "MXN";
            String description = "Donación ($" + donacionDto.getMonto().toString() + ") de " + donacionDto.getDonador().getCorreo();

            // Guarda el pago en PayPal
            Payment pago = paypalServiceImp.creaPago(
                donacionDto.getMonto(), 
                currency, 
                PaypalPaymentMethod.PAYPAL, 
                PaypalPaymentIntent.SALE,
                description, 
                URL_INDEX, 
                URL_CONSULTAR_TODOS
            );

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
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(MSG_ERROR, "Registro fallido: Error en el servidor");
            return REDIRECT_INDEX;
        }
    }
    
    @GetMapping("/webhook")
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalServiceImp.ejecutaPago(paymentId, payerId);

            if(payment.getState().equals("approved")){
                return REDIRECT_CT;
            }

            return REDIRECT_INDEX;
        } catch (Exception e) {

            e.printStackTrace();
            return REDIRECT_INDEX;
        }
	}

    @GetMapping("/consultarTodos")
    public String consultarTodos(Model model,
            RedirectAttributes redirectAttributes, Pageable pageable) {
        Page<Donacion> listaDonaciones = donacionServiceImp
                .listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("fechaDonacion").descending()));
        model.addAttribute("listaDonaciones", listaDonaciones);
        return "donacion/listaDonaciones";
    }

    @GetMapping("/donar")
    public String crearDonacion(@ModelAttribute("donacion") DonacionDto donacionDto, Model modelo) {
        return "usuario/formDonacion";
    }

    @GetMapping("/consultaUnica/{id}")
    public String consultaUnica(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        try {
            Donacion donacion = donacionServiceImp.obtenerDonacion(id);
            
            if (donacion != null) {
                modelo.addAttribute("donacion", donacion);
                return "usuario/listarDonacion";
            }

            redirectAttributes.addFlashAttribute(MSG_ERROR, "Donacion no econtrada");
            return REDIRECT_CT;
        } catch (Exception e) {
            e.printStackTrace();
            return REDIRECT_CT;
        }
    }
}
