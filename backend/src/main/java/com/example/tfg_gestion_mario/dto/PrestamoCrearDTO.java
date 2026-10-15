package com.example.tfg_gestion_mario.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos necesarios para registrar un nuevo préstamo")
public class PrestamoCrearDTO {

    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "ID del usuario que solicita el préstamo", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long usuarioId;

    @NotNull(message = "El libro es obligatorio")
    @Schema(description = "ID del libro a prestar", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long libroId;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    @Schema(description = "Fecha en la que se realiza el préstamo", example = "2026-03-16", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaPrestamo;

    @Schema(description = "Fecha prevista de devolución", example = "2026-04-16")
    private LocalDate fechaDevolucion;
}