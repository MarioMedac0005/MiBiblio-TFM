package com.example.tfg_gestion_mario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tfg_gestion_mario.dto.ReservaCrearDTO;
import com.example.tfg_gestion_mario.dto.ReservaRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Libro;
import com.example.tfg_gestion_mario.entity.Reserva;
import com.example.tfg_gestion_mario.entity.Usuario;
import com.example.tfg_gestion_mario.mapper.ReservaMapper;
import com.example.tfg_gestion_mario.repository.LibroRepository;
import com.example.tfg_gestion_mario.repository.ReservaRepository;
import com.example.tfg_gestion_mario.repository.UsuarioRepository;
import com.example.tfg_gestion_mario.repository.PrestamoRepository;
import com.example.tfg_gestion_mario.entity.Prestamo;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            PrestamoRepository prestamoRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Page<ReservaRespuestaDTO> obtenerTodas(Pageable pageable) {
        return reservaRepository.findAll(pageable).map(ReservaMapper::toDTO);
    }

    public ReservaRespuestaDTO crear(ReservaCrearDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.isSancionado()) {
            throw new RuntimeException("El usuario está sancionado y no puede hacer reservas");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getEjemplaresDisponibles() > 0) {
            throw new RuntimeException("El libro tiene ejemplares disponibles, no es necesario reservar");
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setLibro(libro);
        reserva.setActiva(true);

        return ReservaMapper.toDTO(reservaRepository.save(reserva));
    }

    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La reserva no existe"));
        reserva.setActiva(false);
        reservaRepository.save(reserva);
    }

    public void cumplir(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La reserva no existe"));
                
        if (!reserva.isActiva()) {
            throw new RuntimeException("La reserva ya no está activa");
        }
        
        Libro libro = reserva.getLibro();
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles para cumplir esta reserva. El libro debe ser devuelto primero.");
        }
        
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(reserva.getUsuario());
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(java.time.LocalDate.now());
        prestamo.setFechaDevolucion(java.time.LocalDate.now().plusDays(7));
        prestamo.setDevuelto(false);
        prestamo.setRenovaciones(0);
        
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        reserva.setActiva(false);
        
        prestamoRepository.save(prestamo);
        reservaRepository.save(reserva);
        libroRepository.save(libro);
    }

    public void eliminar(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("La reserva no existe");
        }
        reservaRepository.deleteById(id);
    }
}
