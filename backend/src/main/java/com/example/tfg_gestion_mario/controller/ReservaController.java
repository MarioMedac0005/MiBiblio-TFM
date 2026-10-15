package com.example.tfg_gestion_mario.controller;

import com.example.tfg_gestion_mario.dto.ReservaCrearDTO;
import com.example.tfg_gestion_mario.dto.ReservaRespuestaDTO;
import com.example.tfg_gestion_mario.service.ReservaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Operaciones para gestionar reservas de libros sin stock")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<Page<ReservaRespuestaDTO>> listar(
        @ParameterObject 
        @PageableDefault(size = 10, sort = "id")
        Pageable pageable
    ) {
        return ResponseEntity.ok(reservaService.obtenerTodas(pageable));
    }

    @PostMapping
    public ResponseEntity<ReservaRespuestaDTO> crear(
            @Valid @RequestBody ReservaCrearDTO reservaDto) {
        ReservaRespuestaDTO creada = reservaService.crear(reservaDto);
        return ResponseEntity.status(201).body(creada);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/prestamo")
    public ResponseEntity<Void> cumplirReserva(@PathVariable Long id) {
        reservaService.cumplir(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
