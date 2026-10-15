package com.example.tfg_gestion_mario.exception;

public class PrestamoNoEncontradoException extends RuntimeException {
    public PrestamoNoEncontradoException(Long id) {
        super("El prestamo con el id " + id + " no existe");
    }
}
