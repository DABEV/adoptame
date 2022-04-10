package mx.edu.utez.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.repository.SolicitudRepository;

@Service
public class SolicitudServiceImp implements SolicitudService {

    @Autowired
    SolicitudRepository repository;

    @Override
    public List<Solicitud> listarSolicitudes() {
        return repository.findAll();
    }

    @Override
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        Solicitud solicitudResultado = null;
        try{
            solicitudResultado = repository.save(solicitud);
        }catch(Exception e){
            //log
        }
        return solicitudResultado;
    }

    @Override
    public Solicitud obtenerSolicitud(Long id) {
        Solicitud solicitudResultado = null;
        Optional<Solicitud> sOptional = repository.findById(id);

        if(sOptional.isPresent()){
            solicitudResultado = sOptional.get();
        }

        return solicitudResultado;
    }

    @Override
    public boolean eliminarSolicitud(Long id) {
        try{
            repository.deleteById(id);
            return true;
        }catch(Exception e){
            //log
        }
        return false;
    }

    @Override
    public boolean rechazarSolicitud(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean aprobarSolicitud(String aprobado,Long id) {
        try{
            repository.updateSolicitudesSetAprobadoForIdNative(aprobado, id);
            return true;
        }catch(Exception e){
            //log
        }
        return false;
    }
    
}
