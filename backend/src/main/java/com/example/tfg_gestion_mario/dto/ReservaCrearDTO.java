package com.example.tfg_gestion_mario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaCrearDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;
}
