package com.example.tfg_gestion_mario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tfg_gestion_mario.dto.UsuarioCrearDTO;
import com.example.tfg_gestion_mario.dto.UsuarioRespuestaDTO;
import com.example.tfg_gestion_mario.entity.Usuario;
import com.example.tfg_gestion_mario.exception.UsuarioNoEncontradoException;
import com.example.tfg_gestion_mario.mapper.UsuarioMapper;
import com.example.tfg_gestion_mario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public java.util.List<UsuarioRespuestaDTO> obtenerTodosList() {
        return usuarioRepository.findAll().stream().map(UsuarioMapper::toDTO).toList();
    }

    public Page<UsuarioRespuestaDTO> obtenerTodos(Pageable pageable, String query) {

        Page<Usuario> usuarios;

        if (query == null || query.isBlank()) {
            usuarios = usuarioRepository.findAll(pageable);
        } else {
            usuarios = usuarioRepository
                .findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    query, query, pageable
                );
        }

        return usuarios.map(UsuarioMapper::toDTO);
    }

    public UsuarioRespuestaDTO obtener(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("El usuario que desea obtener no existe"));

        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioRespuestaDTO crear(UsuarioCrearDTO usuarioDto) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDto);
        Usuario guardado = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(guardado);
    }

    public UsuarioRespuestaDTO actualizar(Long id, UsuarioCrearDTO usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellidos(usuarioDto.getApellidos());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setTelefono(usuarioDto.getTelefono());

        Usuario actualizado = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(actualizado);
    }

    public void quitarSancion(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuario.setSancionado(false);
        usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException(id);
        }

        usuarioRepository.deleteById(id);
    }
}
