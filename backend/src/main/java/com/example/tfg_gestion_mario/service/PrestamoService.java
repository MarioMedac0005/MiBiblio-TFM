package com.example.tfg_gestion_mario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tfg_gestion_mario.dto.PrestamoCrearDTO;
import com.example.tfg_gestion_mario.dto.PrestamoRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Libro;
import com.example.tfg_gestion_mario.entity.Prestamo;
import com.example.tfg_gestion_mario.entity.Usuario;
import com.example.tfg_gestion_mario.mapper.PrestamoMapper;
import com.example.tfg_gestion_mario.repository.LibroRepository;
import com.example.tfg_gestion_mario.repository.PrestamoRepository;
import com.example.tfg_gestion_mario.repository.ReservaRepository;
import com.example.tfg_gestion_mario.repository.UsuarioRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final ReservaRepository reservaRepository;

    public PrestamoService(
            PrestamoRepository prestamoRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            ReservaRepository reservaRepository) {

        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.reservaRepository = reservaRepository;
    }

    public Page<PrestamoRespuestaDTO> obtenerTodos(Pageable pageable, String query) {

        Page<Prestamo> prestamos;

        if (query == null || query.isBlank()) {
            prestamos = prestamoRepository.findAll(pageable);
        } else {
            prestamos = prestamoRepository.findByUsuarioNombreContainingIgnoreCaseOrLibroTituloContainingIgnoreCase(
                    query, query, pageable);
        }

        return prestamos.map(PrestamoMapper::toDTO);
    }

    public PrestamoRespuestaDTO obtener(Long id) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El préstamo no existe"));

        return PrestamoMapper.toDTO(prestamo);
    }

    public PrestamoRespuestaDTO crear(PrestamoCrearDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (usuario.isSancionado()) {
            throw new RuntimeException("El usuario está sancionado y no puede realizar nuevos préstamos");
        }

        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles");
        }

        Prestamo prestamo = PrestamoMapper.toEntity(dto);

        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);

        Prestamo guardado = prestamoRepository.save(prestamo);

        return PrestamoMapper.toDTO(guardado);
    }

    public void devolver(Long id, String estadoDevolucion) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El préstamo no existe"));

        if (!prestamo.isDevuelto()) {

            prestamo.setDevuelto(true);
            prestamo.setEstadoDevolucion(estadoDevolucion);

            if (java.time.LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {
                prestamo.getUsuario().setSancionado(true);
                usuarioRepository.save(prestamo.getUsuario());
            }

            Libro libro = prestamo.getLibro();
            if (!"Perdido".equalsIgnoreCase(estadoDevolucion)) {
                libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
            }

            prestamoRepository.save(prestamo);
        }
    }

    public PrestamoRespuestaDTO renovar(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El préstamo no existe"));

        if (prestamo.isDevuelto()) {
            throw new RuntimeException("El préstamo ya ha sido devuelto");
        }

        if (prestamo.getRenovaciones() >= 2) {
            throw new RuntimeException("Se ha alcanzado el límite máximo de 2 renovaciones");
        }

        boolean hasReservas = !reservaRepository.findByLibroIdAndActivaTrueOrderByFechaReservaAsc(prestamo.getLibro().getId()).isEmpty();
        if (hasReservas) {
            throw new RuntimeException("No se puede renovar porque el libro tiene reservas activas");
        }

        prestamo.setFechaDevolucion(prestamo.getFechaDevolucion().plusDays(7));
        prestamo.setRenovaciones(prestamo.getRenovaciones() + 1);

        Prestamo guardado = prestamoRepository.save(prestamo);
        return PrestamoMapper.toDTO(guardado);
    }

    public void eliminar(Long id) {

        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("El préstamo no existe");
        }

        prestamoRepository.deleteById(id);
    }
}