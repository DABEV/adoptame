package mx.edu.utez.adoptame.service;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Usuario guardarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Usuario usuario);
    Usuario obtenerUsuario(long id);
    boolean eliminarUsuario(long id);
}
