package com.example.tfg_gestion_mario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos de respuesta de un préstamo")
public class PrestamoRespuestaDTO {

    @Schema(description = "Identificador único del préstamo", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario que realizó el préstamo", example = "Mario García")
    private String usuarioNombre;

    @Schema(description = "Título del libro prestado", example = "Don Quijote de la Mancha")
    private String libroTitulo;

    @Schema(description = "Fecha en la que se realizó el préstamo", example = "2026-03-16")
    private LocalDate fechaPrestamo;

    @Schema(description = "Fecha prevista o real de devolución", example = "2026-04-16")
    private LocalDate fechaDevolucion;

    @Schema(description = "Indica si el libro ha sido devuelto", example = "false")
    private boolean devuelto;

    @Schema(description = "Fecha y hora de creación del préstamo", example = "2026-03-16T14:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Número de renovaciones realizadas", example = "0")
    private int renovaciones;

    @Schema(description = "Estado físico en el que se devolvió el libro", example = "Perfecto")
    private String estadoDevolucion;
}