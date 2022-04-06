package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Donacion;

public interface DonacionService {
    List<Donacion> listarDonaciones();
    Donacion guardarDonacion(Donacion donacion);
    Donacion actualizarDonacion(Donacion favorito);
    Donacion obtenerDonacion(Long id);
    boolean eliminarDonacion(Long id);
}
