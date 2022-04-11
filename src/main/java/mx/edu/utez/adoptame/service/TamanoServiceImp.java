package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.repository.TamanoRepository;

public class TamanoServiceImp implements TamanoService{
    @Autowired
    TamanoRepository repository;
    @Override
    public List<Tamano> listarTamanos() {
        return repository.findAll();
    }

    @Override
    public Tamano guardarTamano(Tamano tamano) {
        Tamano tamanoObjeto = null;
        try{
            tamanoObjeto = repository.save(tamano);
        }catch(Exception e){
            //
        }
        return tamanoObjeto;
    }

    @Override
    public Tamano obtenerTamano(Long id) {
        Tamano tamanoObjeto = null;
        Optional<Tamano> tamanoOpcional = repository.findById(id);

        if(tamanoOpcional.isPresent()){
            tamanoObjeto = tamanoOpcional.get();
        }

        return tamanoObjeto;
    }

    @Override
    public boolean eliminarTamano(long id) {
        try{
            repository.deleteById(id);
            return true;
        }catch(Exception e){
            //
        }
        return false;
    }
}
