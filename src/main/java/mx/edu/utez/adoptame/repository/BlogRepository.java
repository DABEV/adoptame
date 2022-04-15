package mx.edu.utez.adoptame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.adoptame.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByEsPrincipal(Boolean esPrincipal);
}
