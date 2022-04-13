package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private Usuario usuario;

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            usuarios = repository.findAll();
        } catch (Exception e) {
            // log
        }

        return usuarios;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        this.usuario = usuario;

        try {
            this.usuario = repository.save(usuario);
        } catch (Exception e) {
            // log
        }

        return this.usuario;
    }

    @Override
    public Usuario obtenerUsuario(Long id) {
        this.usuario = null;

        try {
            this.usuario = repository.getById(id);
        } catch (Exception e) {
            // log
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
            // log
        }

        return respuesta;
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        this.usuario = null;

        try {
            this.usuario = repository.findByCorreo(correo);
        } catch (Exception e) {
            // log
        }

        return this.usuario;
    }
    
}
