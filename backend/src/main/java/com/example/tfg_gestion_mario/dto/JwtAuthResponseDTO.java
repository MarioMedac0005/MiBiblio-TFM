package com.example.tfg_gestion_mario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    
    public JwtAuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
