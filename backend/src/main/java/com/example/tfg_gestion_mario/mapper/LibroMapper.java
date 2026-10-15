package com.example.tfg_gestion_mario.mapper;

import com.example.tfg_gestion_mario.dto.LibroCrearDTO;
import com.example.tfg_gestion_mario.dto.LibroRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Libro;

public class LibroMapper {
    public static Libro toEntity(LibroCrearDTO dto) {
        Libro libro = new Libro();

        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setEditorial(dto.getEditorial());
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());

        return libro;
    }

    public static LibroRespuestaDTO toDTO(Libro libro) {
        LibroRespuestaDTO dto = new LibroRespuestaDTO();

        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setIsbn(libro.getIsbn());
        dto.setEditorial(libro.getEditorial());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        dto.setEjemplaresDisponibles(libro.getEjemplaresDisponibles());
        dto.setFechaCreacion(libro.getFechaCreacion());

        return dto;
    }
}
