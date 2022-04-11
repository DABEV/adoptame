package mx.edu.utez.adoptame.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Rol;
import mx.edu.utez.adoptame.repository.RolRepository;

@Service
public class RolServiceImp implements RolService {

    @Autowired
    private RolRepository repository;

    @Override
    public List<Rol> listarRoles() {
        List<Rol> roles = new ArrayList<>();

        try {
            roles = repository.findAll();
        } catch (Exception e) {
            // log
        }

        return roles;
    }

    @Override
    public Rol obtenerRol(Long id) {
        Rol rol = null;

        try {
            rol = repository.getById(id);
        } catch (Exception e) {
            // log
        }

        return rol;
    }
}
