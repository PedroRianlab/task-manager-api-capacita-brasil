package com.example.taskmanagerapi.controller;

import com.example.taskmanagerapi.dto.*;
import com.example.taskmanagerapi.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefas")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) { this.tarefaService = tarefaService; }

    @PostMapping
    @Operation(summary = "Cria uma tarefa para o usuário autenticado")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Tarefa criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<TarefaResponseDTO> criar(@Valid @RequestBody TarefaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Lista as tarefas do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    public List<TarefaResponseDTO> listar(@RequestParam(required = false) Boolean concluida) {
        return tarefaService.listar(concluida);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma tarefa própria")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "403", description = "Tarefa pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    public TarefaResponseDTO buscar(@PathVariable Long id) { return tarefaService.buscar(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa própria")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "403", description = "Operação não autorizada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    public TarefaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TarefaRequestDTO request) {
        return tarefaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tarefa própria")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Tarefa excluída"),
            @ApiResponse(responseCode = "403", description = "Operação não autorizada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { tarefaService.excluir(id); }
}
