package com.example.tfg_gestion_mario.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Long id) {
        super("El usuario con el id " + id + " no existe");
    }
}
