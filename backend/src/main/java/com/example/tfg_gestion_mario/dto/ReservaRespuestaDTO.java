package com.example.tfg_gestion_mario.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReservaRespuestaDTO {
    private Long id;
    private UsuarioRespuestaDTO usuario;
    private LibroRespuestaDTO libro;
    private LocalDateTime fechaReserva;
    private boolean activa;
}
