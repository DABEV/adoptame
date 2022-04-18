package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.model.Usuario;

public interface DonacionService {
    boolean guardarDonacion(Donacion donacion, HttpSession session);
    List<Donacion> listarDonaciones();
    Page<Donacion> listarDonacionesDelUsuario(Usuario usuario, Pageable pageable);
    List<Donacion> listarDonacionesDelUsuario(Usuario usuario);
    boolean eliminarDonacion(Long id, HttpSession session);
    Donacion obtenerDonacion(Long id);
    Page<Donacion> listarPaginacion(Pageable page);
    List<Donacion> procedimientoRegistrarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId);
    List<Donacion> procedimientoEliminarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId);
}
