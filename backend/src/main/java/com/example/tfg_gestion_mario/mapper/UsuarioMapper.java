package com.example.tfg_gestion_mario.mapper;

import com.example.tfg_gestion_mario.dto.UsuarioCrearDTO;
import com.example.tfg_gestion_mario.dto.UsuarioRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Usuario;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioCrearDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        return usuario;
    }

    public static UsuarioRespuestaDTO toDTO(Usuario usuario) {
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setSancionado(usuario.isSancionado());

        return dto;
    }
}
