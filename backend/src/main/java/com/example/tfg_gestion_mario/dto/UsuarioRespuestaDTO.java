package com.example.tfg_gestion_mario.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos de respuesta de un usuario")
public class UsuarioRespuestaDTO {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Mario")
    private String nombre;

    @Schema(description = "Apellidos del usuario", example = "García López")
    private String apellidos;

    @Schema(description = "Correo electrónico del usuario", example = "mario@example.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "612345678")
    private String telefono;

    @Schema(description = "Fecha de creación del usuario", example = "2024-06-01T12:00:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Indica si el usuario tiene una sanción activa", example = "false")
    private boolean sancionado;
}