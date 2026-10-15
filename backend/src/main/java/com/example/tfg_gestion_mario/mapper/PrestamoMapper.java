package com.example.tfg_gestion_mario.mapper;

import com.example.tfg_gestion_mario.dto.PrestamoCrearDTO;
import com.example.tfg_gestion_mario.dto.PrestamoRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Prestamo;

public class PrestamoMapper {

    public static Prestamo toEntity(PrestamoCrearDTO dto) {

        Prestamo prestamo = new Prestamo();

        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());
        prestamo.setDevuelto(false);

        return prestamo;
    }

    public static PrestamoRespuestaDTO toDTO(Prestamo prestamo) {

        PrestamoRespuestaDTO dto = new PrestamoRespuestaDTO();

        dto.setId(prestamo.getId());
        dto.setUsuarioNombre(prestamo.getUsuario().getNombre());
        dto.setLibroTitulo(prestamo.getLibro().getTitulo());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucion(prestamo.getFechaDevolucion());
        dto.setDevuelto(prestamo.isDevuelto());
        dto.setFechaCreacion(prestamo.getFechaCreacion());
        dto.setRenovaciones(prestamo.getRenovaciones());
        dto.setEstadoDevolucion(prestamo.getEstadoDevolucion());

        return dto;
    }
}