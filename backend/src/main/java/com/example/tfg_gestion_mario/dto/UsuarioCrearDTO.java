package com.example.tfg_gestion_mario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos necesarios para crear o actualizar un usuario")
public class UsuarioCrearDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Schema(description = "Nombre del usuario", example = "Mario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Schema(description = "Apellidos del usuario", example = "García López", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Schema(description = "Correo electrónico del usuario", example = "mario@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 números")
    @Schema(description = "Número de teléfono (9 dígitos)", example = "612345678")
    private String telefono;
}
