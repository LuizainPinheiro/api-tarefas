package com.tarefasAPI.exception;

public class TarefaJaCadastradaException extends RuntimeException {
    public TarefaJaCadastradaException(String titulo) {
        super("Já existe uma tarefa cadastrada com o título: " + titulo);
    }
}
