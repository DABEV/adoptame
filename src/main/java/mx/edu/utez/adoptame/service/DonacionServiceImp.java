package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.repository.DonacionRepository;
import mx.edu.utez.adoptame.model.Donacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class DonacionServiceImp implements DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;

    @Override
    public List<Donacion> listarDonaciones() {
        return (List<Donacion>) donacionRepository.findAll(Sort.by("fechaDonacion"));
    }

    @Override
    public boolean guardarDonacion(Donacion donacion) {
        try {
            donacionRepository.save(donacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Donacion obtenerDonacion(Long id) {
        Optional<Donacion> obj = donacionRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    @Override
    public boolean eliminarDonacion(Long id) {
        boolean existe = donacionRepository.existsById(id);
        if (existe) {
            donacionRepository.deleteById(id);
            return donacionRepository.existsById(id);
        }
        return false;
    }

    @Override
    public Page<Donacion> listarPaginacion(Pageable page) {
        return donacionRepository.findAll(page);
    }

}
