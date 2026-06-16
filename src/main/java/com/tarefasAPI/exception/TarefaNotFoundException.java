package com.tarefasAPI.exception;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException(Long id) {
        super("Tarefa não encontrada com o ID: " + id);
    }
}
