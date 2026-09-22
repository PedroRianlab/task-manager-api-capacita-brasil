package com.example.taskmanagerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token de autenticação")
public record AuthResponseDTO(@Schema(example = "eyJhbGciOiJIUzI1NiJ9...") String token) {}
