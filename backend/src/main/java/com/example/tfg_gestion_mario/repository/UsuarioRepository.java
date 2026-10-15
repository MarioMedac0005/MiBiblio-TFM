package com.example.tfg_gestion_mario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tfg_gestion_mario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String nombre,
        String email,
        Pageable pageable
    );
}
