package mx.edu.utez.adoptame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Caracter;
import mx.edu.utez.adoptame.repository.CaracterRepository;

@Service
public class CaracterServiceImpl implements CaracterService {

    @Autowired
    private CaracterRepository caracterRepository;

    @Override
    public List<Caracter> listarCaracteres() {
        return caracterRepository.findAll();
    }

    @Override
    public Caracter obtenerCaracter(Long id) {
        return caracterRepository.getById(id);
    }
    
}
