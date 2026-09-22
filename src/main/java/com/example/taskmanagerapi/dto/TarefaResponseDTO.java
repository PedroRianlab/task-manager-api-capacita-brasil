package com.example.taskmanagerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de uma tarefa")
public record TarefaResponseDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Estudar Spring Security") String descricao,
        @Schema(example = "ALTA") String prioridade,
        @Schema(example = "false") boolean concluida) {}
