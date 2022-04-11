package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    
}
