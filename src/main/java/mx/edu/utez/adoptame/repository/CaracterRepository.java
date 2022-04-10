package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Caracter;

public interface CaracterRepository extends JpaRepository<Caracter, Long>{
    
}
