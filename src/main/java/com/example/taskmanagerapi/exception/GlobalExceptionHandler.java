package com.example.taskmanagerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> tarefaNaoEncontrada(TarefaNaoEncontradaException ex) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> usuarioNaoAutorizado(UsuarioNaoAutorizadoException ex) {
        return response(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> dadosInvalidos(MethodArgumentNotValidException ex) {
        return response(HttpStatus.BAD_REQUEST, "Dados inválidos");
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now(), "status", status.value(), "error", message));
    }
}
