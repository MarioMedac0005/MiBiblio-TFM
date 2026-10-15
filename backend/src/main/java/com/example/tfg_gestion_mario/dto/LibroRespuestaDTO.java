package com.example.tfg_gestion_mario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos de respuesta de un libro del catálogo")
public class LibroRespuestaDTO {

    @Schema(description = "Identificador único del libro", example = "1")
    private Long id;

    @Schema(description = "Título del libro", example = "Don Quijote de la Mancha")
    private String titulo;

    @Schema(description = "Autor del libro", example = "Miguel de Cervantes")
    private String autor;

    @Schema(description = "Código ISBN del libro", example = "9788491050216")
    private String isbn;

    @Schema(description = "Editorial del libro", example = "Editorial Planeta")
    private String editorial;

    @Schema(description = "Fecha de publicación del libro", example = "2005-11-01")
    private LocalDate fechaPublicacion;

    @Schema(description = "Número de ejemplares disponibles", example = "5")
    private int ejemplaresDisponibles;

    @Schema(description = "Fecha de creación del registro del libro", example = "2024-06-01T12:00:00")
    private LocalDateTime fechaCreacion;
}
