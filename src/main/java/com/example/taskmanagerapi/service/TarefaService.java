package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.dto.*;
import com.example.taskmanagerapi.exception.*;
import com.example.taskmanagerapi.model.Tarefa;
import com.example.taskmanagerapi.model.Usuario;
import com.example.taskmanagerapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public TarefaResponseDTO criar(TarefaRequestDTO request) {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(request.descricao());
        tarefa.setPrioridade(request.prioridade());
        tarefa.setUsuario(usuarioAtual());
        tarefa.setConcluida(false);
        return toResponse(tarefaRepository.save(tarefa));
    }

    public List<TarefaResponseDTO> listar(Boolean concluida) {
        Long usuarioId = usuarioAtual().getId();
        List<Tarefa> tarefas = concluida == null
                ? tarefaRepository.findByUsuarioId(usuarioId)
                : tarefaRepository.findByUsuarioIdAndConcluida(usuarioId, concluida);
        return tarefas.stream().map(this::toResponse).toList();
    }

    public TarefaResponseDTO buscar(Long id) {
        return toResponse(tarefaDoUsuario(id));
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO request) {
        Tarefa tarefa = tarefaDoUsuario(id);
        tarefa.setDescricao(request.descricao());
        tarefa.setPrioridade(request.prioridade());
        return toResponse(tarefaRepository.save(tarefa));
    }

    public void excluir(Long id) {
        tarefaRepository.delete(tarefaDoUsuario(id));
    }

    private Tarefa tarefaDoUsuario(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));
        if (!tarefa.getUsuario().getId().equals(usuarioAtual().getId())) {
            throw new UsuarioNaoAutorizadoException();
        }
        return tarefa;
    }

    private Usuario usuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails details)) {
            throw new UsuarioNaoAutorizadoException();
        }
        return usuarioRepository.findByEmail(details.getUsername())
                .orElseThrow(UsuarioNaoAutorizadoException::new);
    }

    private TarefaResponseDTO toResponse(Tarefa tarefa) {
        return new TarefaResponseDTO(tarefa.getId(), tarefa.getDescricao(),
                tarefa.getPrioridade(), tarefa.isConcluida());
    }
}
