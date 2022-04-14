package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Mascota;
import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.repository.MascotaRepository;

@Service
public class MascotaServiceImp implements MascotaService {

    @Autowired
    MascotaRepository mascotaRepository;

    @Override
    public List<Mascota> listarMascotas(boolean tipoMascota) {
        return mascotaRepository.findByActivoAndTipo(true, tipoMascota);
    }

    @Override
    public List<Mascota> listarMascotas() {
        List<Mascota> mascotas = null;
        try{
            mascotas = mascotaRepository.findByActivo(true);
        }catch (Exception e){
            //log
        }
        return mascotas;
        
    }

    @Override
    public Mascota guardarMascota(Mascota mascota) {
        Mascota mascotaResultante = null;
        try {
            mascotaResultante = mascotaRepository.save(mascota);
        } catch (Exception e) {
            // log
        }
        return mascotaResultante;
    }

    @Override
    public Mascota obtenerMascota(Long id) {
        Mascota mascotaResultante = null;
        Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);

        if (mascotaOpcional.isPresent()) {
            mascotaResultante = mascotaOpcional.get();
        }

        return mascotaResultante;
    }

    @Override
    public boolean eliminarMascota(Long id) {
        try {
            Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
            if (mascotaOpcional.isPresent()) {
                mascotaOpcional.get().setActivo(false);
                mascotaRepository.save(mascotaOpcional.get());
                return true;
            }
        } catch (Exception e) {
            // Log
        }
        return false;
    }

    @Override
    public Mascota validarRegistro(long id, String verificado) {
        Mascota mascota = null;
        try {
            Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
            if (mascotaOpcional.isPresent()) {
                mascota = mascotaOpcional.get();
                mascota.setAprobadoRegistro(verificado);
                mascotaRepository.save(mascota);
            }
        } catch (Exception e) {
            // Log
        }
        return mascota;
    }

    @Override
    public List<Mascota> filtrarPorParametros(Color color, boolean sexo, Tamano tamano) {
        return mascotaRepository.findByColorOrSexoOrTamano(color, sexo, tamano);
    }

}
