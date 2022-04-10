package mx.edu.utez.adoptame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.adoptame.model.Donacion;

public interface DonacionService {
    public boolean guardarDonacion(Donacion donacion);

    public List<Donacion> listarDonaciones();

    public boolean eliminarDonacion(Long id);

    public Donacion obtenerDonacion(Long id);

    public Page<Donacion> listarPaginacion(Pageable page);
}
