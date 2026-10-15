package com.example.tfg_gestion_mario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tfg_gestion_mario.dto.UsuarioCrearDTO;
import com.example.tfg_gestion_mario.dto.UsuarioRespuestaDTO;
import com.example.tfg_gestion_mario.service.UsuarioService;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para gestionar los usuarios de la biblioteca")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve una lista con todos los usuarios registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioRespuestaDTO.class)))
            )
    })
    @GetMapping
    public ResponseEntity<Page<UsuarioRespuestaDTO>> listar(
        @ParameterObject 
        @PageableDefault(size = 10, sort = "id")
        Pageable pageable,
        @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(usuarioService.obtenerTodos(pageable, query));
    }

    @GetMapping("/todos")
    public ResponseEntity<java.util.List<UsuarioRespuestaDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodosList());
    }

    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Devuelve los datos de un usuario específico a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> obtener(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtener(id));
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con los datos proporcionados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado correctamente",
                    content = @Content(schema = @Schema(implementation = UsuarioRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos (errores de validación)",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<UsuarioRespuestaDTO> crear(
            @Valid @RequestBody UsuarioCrearDTO usuarioDto) {
        UsuarioRespuestaDTO usuarioCreado = usuarioService.crear(usuarioDto);
        return ResponseEntity.status(201).body(usuarioCreado);
    }

    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Modifica los datos de un usuario existente identificado por su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = UsuarioRespuestaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioCrearDTO usuarioDto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuarioDto));
    }

    @Operation(
            summary = "Quitar sanción a un usuario",
            description = "Quita la sanción de un usuario de forma manual"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Sanción quitada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/{id}/quitar-sancion")
    public ResponseEntity<Void> quitarSancion(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {
        usuarioService.quitarSancion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina un usuario del sistema a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuario eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}