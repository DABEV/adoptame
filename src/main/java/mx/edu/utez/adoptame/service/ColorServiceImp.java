package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.repository.ColorRepository;

@Service
public class ColorServiceImp implements ColorService {

    @Autowired
    ColorRepository colorRepository;

    @Override
    public List<Color> listarColores() {
        return colorRepository.findAll();
    }

    @Override
    public Color guardarColor(Color color) {
        Color colorResultante = null;
        try{
            colorResultante = colorRepository.save(color);
        }catch(Exception e){
            //log
        }
        return colorResultante;
    }

    @Override
    public Color obtenerColor(Long id) {
        Color colorResultante = null;
        Optional<Color> colorOpcional = colorRepository.findById(id);

        if(colorOpcional.isPresent()){
            colorResultante = colorOpcional.get();
        }

        return colorResultante;
    }

    @Override
    public boolean eliminarColor(Long id) {
        try{
            colorRepository.deleteById(id);
            return true;
        }catch(Exception e){
            //Log
        }
        return false;
    }
    
}
