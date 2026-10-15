package com.example.tfg_gestion_mario.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos necesarios para crear o actualizar un libro en el catálogo")
public class LibroCrearDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede superar los 200 caracteres")
    @Schema(description = "Título del libro", example = "Don Quijote de la Mancha", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede superar los 100 caracteres")
    @Schema(description = "Autor del libro", example = "Miguel de Cervantes", requiredMode = Schema.RequiredMode.REQUIRED)
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    @Schema(description = "Código ISBN del libro", example = "9788491050216", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isbn;

    @NotBlank(message = "La editorial es obligatoria")
    @Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
    @Schema(description = "Editorial que publicó el libro", example = "Editorial Planeta", requiredMode = Schema.RequiredMode.REQUIRED)
    private String editorial;

    @NotNull(message = "La fecha de publicación es obligatoria")
    @PastOrPresent(message = "La fecha de publicación no puede ser futura")
    @Schema(description = "Fecha de publicación del libro (no puede ser futura)", example = "2005-11-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaPublicacion;

    @Min(value = 0, message = "Los ejemplares disponibles no pueden ser negativos")
    @Schema(description = "Número de ejemplares disponibles para préstamo", example = "5", minimum = "0")
    private int ejemplaresDisponibles;
}