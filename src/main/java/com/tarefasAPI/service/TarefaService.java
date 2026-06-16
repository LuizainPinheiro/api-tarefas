package com.tarefasAPI.service;

import com.tarefasAPI.exception.TarefaJaCadastradaException;
import com.tarefasAPI.exception.TarefaNotFoundException;
import com.tarefasAPI.model.StatusTarefa;
import com.tarefasAPI.model.Tarefa;
import com.tarefasAPI.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Tarefa criar(Tarefa tarefa) {
        if (tarefaRepository.existsByTitulo(tarefa.getTitulo())) {
            throw new TarefaJaCadastradaException(tarefa.getTitulo());
        }
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setDataConclusao(null);

        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa tarefa) {
        Tarefa tarefaExist = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));

        tarefaExist.setTitulo(tarefa.getTitulo());
        tarefaExist.setDescricao(tarefa.getDescricao());
        tarefaExist.setStatus(tarefa.getStatus());
        tarefaExist.setDataVencimento(tarefa.getDataVencimento());
        tarefaExist.setDataConclusao(tarefa.getDataConclusao());

        return tarefaRepository.save(tarefaExist);
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));
    }

    public void deletar(Long id) {
        tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException(id));

        tarefaRepository.deleteById(id);
    }

    public Tarefa concluir(Long id) {
        Tarefa tarefa = buscarPorId(id);

        tarefa.setStatus(StatusTarefa.CONCLUIDA);
        tarefa.setDataConclusao(LocalDate.now());

        return tarefaRepository.save(tarefa);
    }

    public Tarefa reabrir(Long id) {
        Tarefa tarefa = buscarPorId(id);

        if (tarefa.getStatus() == StatusTarefa.PENDENTE) {
            throw new IllegalStateException("Uma tarefa que já está PENDENTE não pode ser reaberta.");
        }

        tarefa.setStatus(StatusTarefa.REABERTA);
        tarefa.setDataConclusao(null);

        return tarefaRepository.save(tarefa);
    }
}

