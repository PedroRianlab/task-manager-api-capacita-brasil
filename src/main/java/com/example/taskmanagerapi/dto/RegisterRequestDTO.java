package com.example.taskmanagerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de usuário")
public record RegisterRequestDTO(
        @NotBlank @Schema(example = "Maria") String nome,
        @Email @NotBlank @Schema(example = "maria@email.com") String email,
        @NotBlank @Size(min = 6) @Schema(format = "password", example = "senha123") String senha) {}
