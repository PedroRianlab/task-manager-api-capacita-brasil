package com.example.taskmanagerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais de acesso")
public record LoginRequestDTO(
        @Email @NotBlank @Schema(example = "usuario@email.com") String email,
        @NotBlank @Schema(format = "password", example = "senha123") String senha) {}
