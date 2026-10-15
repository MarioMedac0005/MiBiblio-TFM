package com.example.tfg_gestion_mario.controller;

import com.example.tfg_gestion_mario.dto.PrestamoCrearDTO;
import com.example.tfg_gestion_mario.dto.PrestamoRespuestaDTO;
import com.example.tfg_gestion_mario.service.PrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
@Tag(name = "Préstamos", description = "Operaciones para gestionar los préstamos de libros a usuarios")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(
            summary = "Listar todos los préstamos",
            description = "Devuelve una lista con todos los préstamos registrados, incluyendo nombre del usuario y título del libro"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de préstamos obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrestamoRespuestaDTO.class)))
            )
    })
    @GetMapping
    public ResponseEntity<Page<PrestamoRespuestaDTO>> listar(
        @ParameterObject 
        @PageableDefault(size = 10, sort = "id")
        Pageable pageable,
        @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(prestamoService.obtenerTodos(pageable, query));
    }

    @Operation(
            summary = "Obtener un préstamo por ID",
            description = "Devuelve los datos de un préstamo específico a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Préstamo encontrado",
                    content = @Content(schema = @Schema(implementation = PrestamoRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoRespuestaDTO> obtener(
            @Parameter(description = "ID del préstamo", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtener(id));
    }

    @Operation(
            summary = "Crear un nuevo préstamo",
            description = "Registra un nuevo préstamo asociando un usuario con un libro. "
                    + "Reduce automáticamente los ejemplares disponibles del libro. "
                    + "Se valida que el usuario y el libro existan y que haya ejemplares disponibles."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Préstamo creado correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos o no hay ejemplares disponibles",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario o libro no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping
    public ResponseEntity<PrestamoRespuestaDTO> crear(
            @Valid @RequestBody PrestamoCrearDTO prestamoDto) {
        PrestamoRespuestaDTO prestamoCreado = prestamoService.crear(prestamoDto);
        return ResponseEntity.status(201).body(prestamoCreado);
    }

    @Operation(
            summary = "Devolver un libro prestado",
            description = "Marca un préstamo como devuelto e incrementa los ejemplares disponibles del libro correspondiente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Libro devuelto correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Void> devolver(
            @Parameter(description = "ID del préstamo a devolver", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> payload) {
        
        String estado = payload.getOrDefault("estadoDevolucion", "Perfecto");
        prestamoService.devolver(id, estado);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Renovar un préstamo",
            description = "Añade 7 días a la fecha de devolución si no ha excedido el límite y no hay reservas activas"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Préstamo renovado correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Límite de renovaciones alcanzado o hay reservas",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/{id}/renovar")
    public ResponseEntity<PrestamoRespuestaDTO> renovar(
            @Parameter(description = "ID del préstamo a renovar", required = true, example = "1")
            @PathVariable Long id) {
        PrestamoRespuestaDTO renovado = prestamoService.renovar(id);
        return ResponseEntity.ok(renovado);
    }

    @Operation(
            summary = "Eliminar un préstamo",
            description = "Elimina un registro de préstamo del sistema a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Préstamo eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del préstamo a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        prestamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}