package com.example.taskmanagerapi.exception;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException(Long id) {
        super("Tarefa não encontrada: " + id);
    }
}
