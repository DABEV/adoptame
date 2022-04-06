package mx.edu.utez.adoptame.service;

public interface RolService {
    List<Rol> listarRoles();
    Rol guardarRol(Rol rol);
    Rol actualizarRol(Rol rol);
    Rol obtenerRol(long id);
    boolean eliminarRol(long id);
}
