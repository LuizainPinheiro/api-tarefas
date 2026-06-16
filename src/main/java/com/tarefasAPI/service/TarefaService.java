package com.tarefasAPI.service;

import com.tarefasAPI.model.StatusTarefa;
import com.tarefasAPI.model.Tarefa;
import com.tarefasAPI.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Tarefa criar(Tarefa tarefa) {
        tarefa.setStatus(StatusTarefa.PENDENTE);
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa tarefa) {
        Tarefa tarefaExist = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaExist.setTitulo(tarefa.getTitulo());
        tarefaExist.setDescricao(tarefa.getDescricao());
        tarefaExist.setStatus(tarefa.getStatus());
        tarefaExist.setDataVencimento(tarefa.getDataVencimento());
        tarefaExist.setDataConclusao(tarefa.getDataConclusao());

        return tarefaRepository.save(tarefaExist);
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public void deletar(Long id) {
        tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaRepository.deleteById(id);
    }

    public Tarefa concluir(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setStatus(validarTarefa("CONCLUIDA"));
        tarefa.setDataConclusao(LocalDate.now());

        return tarefaRepository.save(tarefa);
    }

    public StatusTarefa validarTarefa(String status) {
        return Arrays.stream(StatusTarefa.values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Status não reconhecido: " + status));
    }

    public Tarefa reabrir(Long id) {
        Tarefa tarefa = buscarPorId(id);

        if (tarefa.getStatus() == StatusTarefa.PENDENTE) {
            throw new RuntimeException("Uma tarefa que já está PENDENTE não pode ser reaberta.");
        }
        tarefa.setStatus(StatusTarefa.REABERTA);
        return tarefaRepository.save(tarefa);
    }
}

