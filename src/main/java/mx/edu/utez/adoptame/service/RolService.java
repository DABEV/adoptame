package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Rol;

public interface RolService {
    List<Rol> listarRoles();
    Rol guardarRol(Rol rol);
    Rol actualizarRol(Rol rol);
    Rol obtenerRol(Long id);
    boolean eliminarRol(Long id);
}
