package com.example.tfg_gestion_mario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tfg_gestion_mario.dto.LibroCrearDTO;
import com.example.tfg_gestion_mario.dto.LibroRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Libro;
import com.example.tfg_gestion_mario.exception.LibroNoEncontradoException;
import com.example.tfg_gestion_mario.mapper.LibroMapper;
import com.example.tfg_gestion_mario.repository.LibroRepository;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public java.util.List<LibroRespuestaDTO> obtenerTodosList() {
        return libroRepository.findAll().stream().map(LibroMapper::toDTO).toList();
    }

    public Page<LibroRespuestaDTO> obtenerTodos(Pageable pageable, String query) {

        Page<Libro> libros;

        if (query == null || query.isBlank()) {
            libros = libroRepository.findAll(pageable);
        } else {
            libros = libroRepository
                .findByTituloContainingIgnoreCase(
                    query, pageable
                );
        }

        return libros.map(LibroMapper::toDTO);
    }

    public LibroRespuestaDTO obtener(Long id) {
        Libro libro = libroRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("El libro que desea obtener no existe"));

        return LibroMapper.toDTO(libro);
    }

    public LibroRespuestaDTO crear(LibroCrearDTO libroDto) {
        Libro libro = LibroMapper.toEntity(libroDto);
        Libro guardado = libroRepository.save(libro);

        return LibroMapper.toDTO(guardado);
    }

    public LibroRespuestaDTO actualizar(Long id, LibroCrearDTO libroDto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));

        libro.setTitulo(libroDto.getTitulo());
        libro.setAutor(libroDto.getAutor());
        libro.setIsbn(libroDto.getIsbn());
        libro.setEditorial(libroDto.getEditorial());
        libro.setFechaPublicacion(libroDto.getFechaPublicacion());
        libro.setEjemplaresDisponibles(libroDto.getEjemplaresDisponibles());

        Libro actualizado = libroRepository.save(libro);

        return LibroMapper.toDTO(actualizado);
    }

    public void eliminar(Long id) {
        if(!libroRepository.existsById(id)) {
            throw new LibroNoEncontradoException(id);
        }

        libroRepository.deleteById(id);
    }
}
