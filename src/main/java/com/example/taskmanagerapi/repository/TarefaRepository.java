package com.example.taskmanagerapi.repository;

import com.example.taskmanagerapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuarioId(Long usuarioId);
    List<Tarefa> findByUsuarioIdAndConcluida(Long usuarioId, boolean concluida);
    Optional<Tarefa> findByIdAndUsuarioId(Long id, Long usuarioId);
}
