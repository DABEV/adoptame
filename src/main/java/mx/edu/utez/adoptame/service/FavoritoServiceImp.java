package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.repository.FavoritoRepository;

@Service
public class FavoritoServiceImp implements FavoritoService {

    @Autowired
    FavoritoRepository favoritoRepository;

    @Override
    public List<Favorito> listarFavoritos(long idUsuario) {
        return favoritoRepository.findByUsuarioId(idUsuario);
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
        try {
            Optional<Favorito> favoritoOpcional = favoritoRepository.findById(id);

            if (favoritoOpcional.isPresent()) {
                favoritoResultante = favoritoOpcional.get();
            }
        } catch (Exception e) {
            // log
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

    @Override
    public Favorito obtenerPorMascota(long idMascota, long idUsuario) {
        Favorito favorito = null;
        try {
            Optional<Favorito> favoritoOpcional = favoritoRepository.findByMascotaIdAndUsuarioId(idMascota, idUsuario);
            if (favoritoOpcional.isPresent()) {
                favorito = favoritoOpcional.get();
            }
        } catch (Exception e) {
            // log
        }
        return favorito;
    }

}
