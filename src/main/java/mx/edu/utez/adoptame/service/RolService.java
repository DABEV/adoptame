package mx.edu.utez.adoptame.service;

import java.util.List;

import mx.edu.utez.adoptame.model.Rol;

public interface RolService {
    List<Rol> listarRoles();
    Rol obtenerRol(Long id);
    Rol buscarPorNombre(String nombre);
}
