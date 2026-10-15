package com.example.tfg_gestion_mario.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tfg_gestion_mario.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByLibroIdAndActivaTrueOrderByFechaReservaAsc(Long libroId);
}
