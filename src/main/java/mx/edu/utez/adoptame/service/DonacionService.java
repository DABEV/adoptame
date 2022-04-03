package mx.edu.utez.adoptame.service;

public interface DonacionService {
    List<Donacion> listarDonaciones();
    Donacion guardarDonacion(Donacion donacion);
    Donacion actualizarDonacion(Donacion favorito);
    Donacion obtenerDonacion(long id);
    boolean eliminarDonacion(long id);
}
