package com.example.tfg_gestion_mario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tfg_gestion_mario.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Page<Prestamo> findByUsuarioNombreContainingIgnoreCaseOrLibroTituloContainingIgnoreCase(String usuarioNombre, String libroTitulo, Pageable pageable);
}
