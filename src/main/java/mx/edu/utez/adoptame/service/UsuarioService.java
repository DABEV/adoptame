package mx.edu.utez.adoptame.service;

import java.util.Date;
import java.util.List;

import mx.edu.utez.adoptame.model.Usuario;

public interface UsuarioService {
    List<Usuario> listarUsuarios();

    Usuario guardarUsuario(Usuario usuario);

    Usuario obtenerUsuario(Long id);

    boolean eliminarUsuario(Long id);

    Usuario buscarPorCorreo(String correo);

    boolean cambiarContrasena(String contrasena, String correo);

    Usuario procedimientoInicioSesion(Long usuarioId);

    Usuario procedimientoCerrarSesion(Long usuarioId, Date fechaFin);
}
