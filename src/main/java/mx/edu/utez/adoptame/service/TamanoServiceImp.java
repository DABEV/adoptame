package mx.edu.utez.adoptame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.repository.TamanoRepository;

public class TamanoServiceImp implements TamanoService{
    @Autowired
    TamanoRepository repository;
    @Override
    public List<Tamano> listAll() {
        return repository.findAll();
    }

    @Override
    public Tamano save(Tamano tamano) {
        return repository.save(tamano);
    }

    @Override
    public boolean delete(long id) {
        repository.deleteById(id);
        return true;
    }
}
