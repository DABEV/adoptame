package mx.edu.utez.adoptame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    
}
