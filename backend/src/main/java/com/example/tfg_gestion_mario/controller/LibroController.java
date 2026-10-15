package com.example.tfg_gestion_mario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tfg_gestion_mario.dto.LibroCrearDTO;
import com.example.tfg_gestion_mario.dto.LibroRespuestaDTO;
import com.example.tfg_gestion_mario.service.LibroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/libros")
@Tag(name = "Libros", description = "Operaciones para gestionar el catálogo de libros de la biblioteca")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Operation(
            summary = "Listar todos los libros",
            description = "Devuelve una lista con todos los libros disponibles en el catálogo de la biblioteca"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de libros obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LibroRespuestaDTO.class)))
            )
    })
    @GetMapping
    public ResponseEntity<Page<LibroRespuestaDTO>> listar(
        @ParameterObject
        @PageableDefault(size = 10, sort = "id")
        Pageable pageable,
        @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(libroService.obtenerTodos(pageable, query));
    }

    @GetMapping("/todos")
    public ResponseEntity<java.util.List<LibroRespuestaDTO>> listarTodos() {
        return ResponseEntity.ok(libroService.obtenerTodosList());
    }

    @Operation(
            summary = "Obtener un libro por ID",
            description = "Devuelve los datos de un libro específico a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro encontrado",
                    content = @Content(schema = @Schema(implementation = LibroRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<LibroRespuestaDTO> obtener(
            @Parameter(description = "ID del libro", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtener(id));
    }

    @Operation(
            summary = "Crear un nuevo libro",
            description = "Añade un nuevo libro al catálogo de la biblioteca con los datos proporcionados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Libro creado correctamente",
                    content = @Content(schema = @Schema(implementation = LibroRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos (errores de validación)",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<LibroRespuestaDTO> crear(
            @Valid @RequestBody LibroCrearDTO libroDto) {
        LibroRespuestaDTO libroCreado = libroService.crear(libroDto);
        return ResponseEntity.status(201).body(libroCreado);
    }

    @Operation(
            summary = "Actualizar un libro existente",
            description = "Modifica los datos de un libro existente identificado por su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = LibroRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<LibroRespuestaDTO> actualizar(
            @Parameter(description = "ID del libro a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody LibroCrearDTO libroDto) {
        return ResponseEntity.ok(libroService.actualizar(id, libroDto));
    }

    @Operation(
            summary = "Eliminar un libro",
            description = "Elimina un libro del catálogo a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Libro eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del libro a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}