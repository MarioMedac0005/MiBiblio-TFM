package com.example.tfg_gestion_mario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tfg_gestion_mario.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Page<Libro> findByTituloContainingIgnoreCase(
        String titulo,
        Pageable pageable
    );
}
