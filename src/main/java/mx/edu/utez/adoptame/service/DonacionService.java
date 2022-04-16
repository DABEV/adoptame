package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.adoptame.model.Donacion;

public interface DonacionService {
    public boolean guardarDonacion(Donacion donacion, HttpSession session);

    public List<Donacion> listarDonaciones();

    public boolean eliminarDonacion(Long id, HttpSession session);

    public Donacion obtenerDonacion(Long id);

    public Page<Donacion> listarPaginacion(Pageable page);

    List<Donacion> procedimientoRegistrarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId);


    List<Donacion> procedimientoEliminarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId);
}
