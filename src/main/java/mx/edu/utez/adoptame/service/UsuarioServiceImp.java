package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.repository.UsuarioRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
@Transactional
public class UsuarioServiceImp implements UsuarioService {

    Log log = LogFactory.getLog(getClass());

    private Usuario usuario;

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            usuarios = repository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        this.usuario = usuario;

        try {
            this.usuario = repository.save(usuario);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return this.usuario;
    }

    @Override
    public Usuario obtenerUsuario(Long id) {
        this.usuario = null;

        try {
            this.usuario = repository.getById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return this.usuario;
    }

    @Override
    public boolean eliminarUsuario(Long id) {
        boolean respuesta = false;
        try {
            repository.deleteById(id);
            respuesta = !repository.existsById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return respuesta;
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        this.usuario = null;
        try {
            this.usuario = repository.findByCorreo(correo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return this.usuario;
    }

    @Override
    public boolean cambiarContrasena(String contrasena, String correo) {
        try {
            repository.updatePassword(contrasena, correo);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario procedimientoInicioSesion(Long usuarioId) {
        try {

            repository.registroSesion(usuarioId);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario procedimientoCerrarSesion(Long usuarioId, Date fechaFin) {
        try {

            repository.registroCerrarSesion(usuarioId, fechaFin);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
