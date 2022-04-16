package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.repository.DonacionRepository;
import mx.edu.utez.adoptame.dto.UsuarioDto;
import mx.edu.utez.adoptame.model.Donacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class DonacionServiceImp implements DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;
    
    @Override
    public List<Donacion> listarDonaciones() {
        return donacionRepository.findAll(Sort.by("fechaDonacion"));
    }

    @Override
    public boolean guardarDonacion(Donacion donacion, HttpSession session) {
        try {
            UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute("usuario");
            Long idUsuario = usuarioDto.getId();
            procedimientoRegistrarDonacion(idUsuario, donacion.getAutorizacion(), donacion.getEstado(),
                    donacion.getFechaDonacion(),
                    donacion.getMonto(), donacion.getDonador().getId());
            donacionRepository.save(donacion);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Donacion obtenerDonacion(Long id) {
        Optional<Donacion> obj = donacionRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    @Override
    public boolean eliminarDonacion(Long id, HttpSession session) {
        Donacion donacion = donacionRepository.getById(id);
        UsuarioDto usuarioDto = (UsuarioDto) session.getAttribute("usuario");
        Long idUsuario = usuarioDto.getId();

        procedimientoEliminarDonacion(idUsuario, donacion.getAutorizacion(), donacion.getEstado(),
                donacion.getFechaDonacion(), donacion.getMonto(),
                donacion.getDonador().getId());

        boolean existe = donacionRepository.existsById(id);

        if (existe) {
            donacionRepository.deleteById(id);
            return donacionRepository.existsById(id);
        }
        return false;
    }

    @Override
    public Page<Donacion> listarPaginacion(Pageable page) {
        return donacionRepository.findAll(page);
    }

    @Override
    public List<Donacion> procedimientoRegistrarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId) {
        List<Donacion> list = new ArrayList<>();
        try {
            list = donacionRepository.registroDonacion(idUsuario, autorizacion, estado, fechaDonacion, monto,
                    donadorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Donacion> procedimientoEliminarDonacion(Long idUsuario, String autorizacion, Boolean estado,
            Date fechaDonacion, Double monto, Long donadorId) {
        List<Donacion> list = new ArrayList<>();

        try {
            list = donacionRepository.eliminarDonacion(idUsuario, autorizacion, estado, fechaDonacion, monto,
                    donadorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
