package com.example.tfg_gestion_mario.mapper;

import com.example.tfg_gestion_mario.dto.ReservaRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Reserva;

public class ReservaMapper {
    public static ReservaRespuestaDTO toDTO(Reserva reserva) {
        if (reserva == null) return null;

        ReservaRespuestaDTO dto = new ReservaRespuestaDTO();
        dto.setId(reserva.getId());
        dto.setUsuario(UsuarioMapper.toDTO(reserva.getUsuario()));
        dto.setLibro(LibroMapper.toDTO(reserva.getLibro()));
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setActiva(reserva.isActiva());

        return dto;
    }
}
