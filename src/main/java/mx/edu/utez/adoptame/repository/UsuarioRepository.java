package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
