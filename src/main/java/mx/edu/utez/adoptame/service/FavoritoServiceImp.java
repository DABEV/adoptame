package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.model.Usuario;
import mx.edu.utez.adoptame.repository.FavoritoRepository;

@Service
public class FavoritoServiceImp implements FavoritoService {

    @Autowired
    FavoritoRepository favoritoRepository;

    @Override
    public List<Favorito> listarFavoritos(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }

    @Override
    public Favorito guardarFavorito(Favorito favorito) {
        Favorito favoritoResultante = null;
        try {
            favoritoResultante = favoritoRepository.save(favorito);
        } catch (Exception e) {
            // log
        }
        return favoritoResultante;
    }

    @Override
    public Favorito obtenerFavorito(Long id) {
        Favorito favoritoResultante = null;
        Optional<Favorito> favoritoOpcional = favoritoRepository.findById(id);

        if (favoritoOpcional.isPresent()) {
            favoritoResultante = favoritoOpcional.get();
        }
        return favoritoResultante;
    }

    @Override
    public boolean eliminarFavorito(Long id) {
        try {
            Optional<Favorito> favoritoOpcional = favoritoRepository.findById(id);
            if (favoritoOpcional.isPresent()) {
                favoritoRepository.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            // Log
        }
        return false;
    }

}
