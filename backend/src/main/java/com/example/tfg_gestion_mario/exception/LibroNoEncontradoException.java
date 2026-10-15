package com.example.tfg_gestion_mario.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(Long id) {
        super("El libro con el id " + id + " no existe");
    }
}
