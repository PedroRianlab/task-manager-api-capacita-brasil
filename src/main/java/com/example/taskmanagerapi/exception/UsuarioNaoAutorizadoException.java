package com.example.taskmanagerapi.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {
    public UsuarioNaoAutorizadoException() {
        super("Usuário não autorizado a acessar esta tarefa");
    }
}
