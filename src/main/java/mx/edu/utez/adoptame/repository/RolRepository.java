package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
    
}
