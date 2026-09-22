package com.example.taskmanagerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para criação ou alteração de uma tarefa")
public record TarefaRequestDTO(
        @NotBlank @Schema(example = "Estudar Spring Security") String descricao,
        @NotBlank @Schema(example = "ALTA") String prioridade) {}
